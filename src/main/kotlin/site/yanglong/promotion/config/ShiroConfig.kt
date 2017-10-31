package site.yanglong.promotion.config

import org.apache.commons.collections.MapUtils
import org.apache.shiro.authc.credential.CredentialsMatcher
import org.apache.shiro.cache.ehcache.EhCacheManager
import org.apache.shiro.codec.Base64
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.filter.authc.AnonymousFilter
import org.apache.shiro.web.filter.authc.LogoutFilter
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter
import org.apache.shiro.web.filter.authc.UserFilter
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
import org.apache.shiro.web.mgt.CookieRememberMeManager
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.servlet.AbstractShiroFilter
import org.apache.shiro.web.servlet.SimpleCookie
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager
import org.slf4j.LoggerFactory
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.cache.CacheManager
import org.springframework.cache.ehcache.EhCacheCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import site.yanglong.promotion.config.shiro.DrCredentialsMatcher
import site.yanglong.promotion.config.shiro.authentication.RealmService
import site.yanglong.promotion.config.shiro.authentication.ShiroRealm
import site.yanglong.promotion.config.shiro.dynamic.DynamicPermissionServiceImpl
import site.yanglong.promotion.config.shiro.dynamic.JdbcPermissionDao
import javax.servlet.Filter

@Configuration
@AutoConfigureAfter(ShiroLifeCycleConfig::class,CacheManager::class)
class ShiroConfig {
    private val log = LoggerFactory.getLogger(ShiroConfig::class.java)
    @Autowired
    private var shiroProps:ShiroProperties?=null
    @Autowired
    private var cachemanager: EhCacheCacheManager? = null
    private var shiroFilter: ShiroFilterFactoryBean? = null

    @Bean(name = arrayOf("ehCacheManager"))
    fun ehCacheManager(): EhCacheManager {
        val ehCacheManager = EhCacheManager()
        val manager = cachemanager?.cacheManager
        ehCacheManager.cacheManager = manager
        return ehCacheManager
    }

    /**
     * 生成realm
     *
     * @return shiroRealm
     */
    @Bean(name = arrayOf("shiroRealm"))
    @DependsOn("lifecycleBeanPostProcessor")
    fun shiroRealm(realmService: RealmService, credentialsMatcher: CredentialsMatcher): ShiroRealm {
        val shiroRealm = ShiroRealm()
        shiroRealm.name = "drRealm"
        shiroRealm.identity_in_map_key=shiroProps!!.identityInMapKey
        shiroRealm.password_in_map_key=shiroProps!!.passwordInMapKey
        shiroRealm.isEnablePerms=shiroProps!!.enablePerms
        shiroRealm.isEnableRoles=shiroProps!!.enableRoles
        shiroRealm.perms_in_map_key=shiroProps!!.permsInMapKey
        shiroRealm.roles_in_map_key=shiroProps!!.rolesInMapKey
        shiroRealm.user_status_in_map_key=shiroProps!!.userStatusInMapKey
        shiroRealm.user_status_forbidden=shiroProps!!.userStatusForbidden
        shiroRealm.user_status_locked=shiroProps!!.userStatusLocked
        shiroRealm.realmService = realmService
        shiroRealm.cacheManager = ehCacheManager()
        shiroRealm.credentialsMatcher = credentialsMatcher
        shiroRealm.isCachingEnabled = shiroProps!!.cachingEnabled
        shiroRealm.isAuthenticationCachingEnabled = shiroProps!!.authenticationCachingEnabled//认证缓存
        shiroRealm.authenticationCacheName = shiroProps!!.authenticationCacheName
        shiroRealm.isAuthorizationCachingEnabled = shiroProps!!.authorizationCachingEnabled//授权缓存
        shiroRealm.authorizationCacheName = shiroProps!!.authorizationCacheName
        return shiroRealm
    }

    /**
     * 代理
     *
     * @return
     */
    @Bean
    fun advisorAutoProxyCreator(): DefaultAdvisorAutoProxyCreator {
        val advisorAutoProxyCreator = DefaultAdvisorAutoProxyCreator()
        advisorAutoProxyCreator.isProxyTargetClass = true
        return advisorAutoProxyCreator
    }

    /**
     * 记住我cookie
     */
    @Bean(name = arrayOf("rememberMeCookie"))
    fun rememberMeCookie(): SimpleCookie {
        val cookie = SimpleCookie(shiroProps!!.rmCookieName)
        cookie.isHttpOnly = shiroProps!!.cookieIsHttpOnly
        cookie.maxAge = shiroProps!!.rmCookieMaxAge//有效期，秒，7天
        cookie.path = shiroProps!!.rmCookiePath
        return cookie
    }

    /**
     * 记住我cookie管理器
     */
    @Bean
    fun cookieRememberMeManager(): CookieRememberMeManager {
        val remember = CookieRememberMeManager()
        remember.cipherKey = Base64.decode(shiroProps!!.rmCipherKey)//用于cookie加密的key
        remember.cookie = rememberMeCookie()//cookie
        return remember
    }

    /**
     * 会话dao，如果要进行集群或者分布式应用，这里可以改造为自己的会话dao，也可以对DAO的缓存管理器进行改造
     * {@link #ehCacheManager()}
     */
    @Bean
    fun sessionDAO(): EnterpriseCacheSessionDAO {
        val sessionDAO = EnterpriseCacheSessionDAO()
        sessionDAO.activeSessionsCacheName = shiroProps!!.sessionCacheName//session缓存的名称
        sessionDAO.cacheManager = ehCacheManager()//缓存管理器
        return sessionDAO
    }

    /**
     * 会话cookie
     */
    @Bean
    fun sessionIdCookie(): SimpleCookie {
        val sessionIdCookie = SimpleCookie(shiroProps!!.sessionCookieName)
        sessionIdCookie.domain = shiroProps!!.sessionCookieDomain//注意用域名时必须匹配或者设置跨域
        sessionIdCookie.isHttpOnly = shiroProps!!.cookieIsHttpOnly
        sessionIdCookie.maxAge = shiroProps!!.sessionCookieMaxAge//cookie有效期，单位秒，-1为浏览器进度
        sessionIdCookie.path = shiroProps!!.sessionCookiePath//cookie路径
        return sessionIdCookie
    }

    /**
     * 会话管理器
     */
    @Bean
    fun sessionManager(): DefaultWebSessionManager {
        val sessionManager = DefaultWebSessionManager()
        sessionManager.sessionDAO = sessionDAO()
        sessionManager.isSessionIdCookieEnabled = true//使用cookie传递sessionId
        sessionManager.sessionIdCookie = sessionIdCookie()
        sessionManager.globalSessionTimeout = shiroProps!!.globalSessionTimeout//服务端session过期时间，单位毫秒
        return sessionManager
    }

    /**
     * securityManager
     *
     * @return securityManager
     */
    @Bean(name = arrayOf("securityManager"))
    fun webSecurityManager(shiroRealm: ShiroRealm): DefaultWebSecurityManager {
        val securityManager = DefaultWebSecurityManager()
        securityManager.setRealm(shiroRealm)
        securityManager.cacheManager = ehCacheManager()
        securityManager.rememberMeManager = cookieRememberMeManager()
        securityManager.sessionManager = sessionManager()
        return securityManager
    }

    /**
     * 授权验证代理设置
     * @param securityManager
     * @return
     */
    @Bean
    fun authorizationAttributeSourceAdvisor(securityManager: DefaultWebSecurityManager): AuthorizationAttributeSourceAdvisor {
        val authorizationAttributeSourceAdvisor = AuthorizationAttributeSourceAdvisor()
        authorizationAttributeSourceAdvisor.securityManager = securityManager
        return authorizationAttributeSourceAdvisor
    }

    /**
     * 凭证匹配器
     * @return
     */
    @Bean(name = arrayOf("credentialsMatcher"))
    fun credentialsMatcher(): CredentialsMatcher {
        return DrCredentialsMatcher()
    }

    /**
     * filter工厂
     * @return
     */
    @Bean(name = arrayOf("shiroFilter"))
    fun shiroFilter(webSecurityManager: DefaultWebSecurityManager): ShiroFilterFactoryBean {
        val shiroFilter = ShiroFilterFactoryBean()
        shiroFilter.loginUrl = shiroProps!!.loginUrl
        shiroFilter.successUrl = shiroProps!!.successUrl
        shiroFilter.unauthorizedUrl = shiroProps!!.unauthorizedUrl
        if (MapUtils.isNotEmpty(shiroProps!!.definitionMap)) {
            shiroFilter.filterChainDefinitionMap = shiroProps!!.definitionMap
        }
        shiroFilter.securityManager = webSecurityManager
        val filters = HashMap<String, Filter>()
        if (MapUtils.isNotEmpty(shiroProps!!.filtersMap)) {
            shiroProps!!.filtersMap?.forEach { t, u -> filters.put(t, (Class.forName(u).newInstance()) as Filter) }
        } else {
            filters.put("anon", AnonymousFilter())
            filters.put("authc", PassThruAuthenticationFilter())
            filters.put("logout", LogoutFilter())
            filters.put("roles", RolesAuthorizationFilter())
            filters.put("perms", PermissionsAuthorizationFilter())
            filters.put("user", UserFilter())
        }
        shiroFilter.filters = filters
        log.debug("shiro初始化完成，filter数量为:{}", shiroFilter.filters.size)
        this.shiroFilter = shiroFilter
        return shiroFilter
    }

    @Bean("dynamicPermissionService")
    @ConditionalOnBean(AbstractShiroFilter::class, ShiroFilterFactoryBean::class)
    fun filterChainDefinitionsFactory(jdbcPermissionDao: JdbcPermissionDao): DynamicPermissionServiceImpl {
        val service = DynamicPermissionServiceImpl()
        service.shiroFilter = shiroFilter?.`object` as? AbstractShiroFilter
        service.dynamicPermissionDao = jdbcPermissionDao
        service.definitions = shiroProps!!.definitions
        return service
    }
}