package site.yanglong.promotion.config

import org.apache.shiro.authc.credential.CredentialsMatcher
import org.apache.shiro.cache.ehcache.EhCacheManager
import org.apache.shiro.codec.Base64
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.filter.authc.AnonymousFilter
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter
import org.apache.shiro.web.filter.authc.LogoutFilter
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
import org.springframework.cache.ehcache.EhCacheCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.context.annotation.Primary
import site.yanglong.promotion.config.shiro.DrCredentialsMatcher
import site.yanglong.promotion.config.shiro.authentication.RealmService
import site.yanglong.promotion.config.shiro.authentication.ShiroRealm
import site.yanglong.promotion.config.shiro.dynamic.DynamicPermissionServiceImpl
import site.yanglong.promotion.config.shiro.dynamic.JdbcPermissionDao
import site.yanglong.promotion.context
import java.util.*
import javax.servlet.Filter

@Configuration
class ShiroConfig {
    private val log = LoggerFactory.getLogger(ShiroConfig::class.java)
    @Autowired
    private var cachemanager:EhCacheCacheManager?=null

    @Bean(name = arrayOf("ehCacheManager"))
    @Primary
    fun ehCacheManager(): EhCacheManager {
        val ehCacheManager=EhCacheManager()
        val manager=cachemanager?.cacheManager
        ehCacheManager.cacheManager=manager
      return ehCacheManager
    }

    /**
     * 生成realm
     *
     * @return shiroRealm
     */
    @Bean(name = arrayOf("shiroRealm"))
    @DependsOn("lifecycleBeanPostProcessor")
    fun shiroRealm(realmService: RealmService): ShiroRealm {
        val shiroRealm = ShiroRealm()
        shiroRealm.name="drRealm"
        shiroRealm.realmService=realmService
        shiroRealm.cacheManager = ehCacheManager()
        shiroRealm.credentialsMatcher= credentialsMatcher()
        shiroRealm.isCachingEnabled=true
        shiroRealm.isAuthenticationCachingEnabled=true//认证缓存
        shiroRealm.authenticationCacheName="authentication_cache"
        shiroRealm.isAuthorizationCachingEnabled=true//授权缓存
        shiroRealm.authorizationCacheName="authorization_cache"
        return shiroRealm
    }

    /**
     * shiro会话生命周期管理
     *
     * @return
     */
    @Bean(name = arrayOf("lifecycleBeanPostProcessor"))
    fun lifecycleBeanPostProcessor(): LifecycleBeanPostProcessor {
        return LifecycleBeanPostProcessor()
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
        val cookie=SimpleCookie("rememberMe")
        cookie.isHttpOnly=true
        cookie.maxAge=604800//有效期，秒，7天
        cookie.path="/"
        return cookie
    }

    /**
     * 记住我cookie管理器
     */
    @Bean
    fun cookieRememberMeManager(): CookieRememberMeManager {
        val remember=CookieRememberMeManager()
        remember.cipherKey=Base64.decode("4AvVhmFLUs0KTA3Kprsdag==")//用于cookie加密的key
        remember.cookie=rememberMeCookie()//cookie
        return remember
    }

    /**
     * 会话dao，如果要进行集群或者分布式应用，这里可以改造为自己的会话dao，也可以对DAO的缓存管理器进行改造
     * {@link #ehCacheManager()}
     */
    @Bean
    fun sessionDAO(): EnterpriseCacheSessionDAO {
        val sessionDAO=EnterpriseCacheSessionDAO()
        sessionDAO.activeSessionsCacheName="active_session_cache"//session缓存的名称
        sessionDAO.cacheManager= ehCacheManager()//缓存管理器
        return sessionDAO
    }

    /**
     * 会话cookie
     */
    @Bean
    fun sessionIdCookie():SimpleCookie{
        val sessionIdCookie=SimpleCookie("JID")
        sessionIdCookie.domain="yanglong.com"//注意用域名时必须匹配或者设置跨域
        sessionIdCookie.isHttpOnly=false
        sessionIdCookie.maxAge=-1//cookie有效期，单位秒，-1为浏览器进度
        sessionIdCookie.path="/"//cookie路径
        return sessionIdCookie
    }

    /**
     * 会话管理器
     */
    @Bean
    fun sessionManager(): DefaultWebSessionManager {
        val sessionManager=DefaultWebSessionManager()
        sessionManager.sessionDAO=sessionDAO()
        sessionManager.isSessionIdCookieEnabled=true//使用cookie传递sessionId
        sessionManager.sessionIdCookie=sessionIdCookie()
        sessionManager.globalSessionTimeout=1800000//服务端session过期时间，单位毫秒
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
        securityManager.rememberMeManager=cookieRememberMeManager()
        securityManager.sessionManager=sessionManager()
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
        shiroFilter.loginUrl = "/login"
        shiroFilter.successUrl = "/index"
        shiroFilter.unauthorizedUrl = "/forbidden"
        val filterChainDefinitionMapping = LinkedHashMap<String, String>()
        filterChainDefinitionMapping.put("/admin", "authc,roles[admin]")
        shiroFilter.filterChainDefinitionMap = filterChainDefinitionMapping
        shiroFilter.securityManager = webSecurityManager
        val filters = HashMap<String, Filter>()
        filters.put("anon", AnonymousFilter())
        filters.put("authc", FormAuthenticationFilter())
        filters.put("logout", LogoutFilter())
        filters.put("roles", RolesAuthorizationFilter())
        filters.put("perms", PermissionsAuthorizationFilter())
        filters.put("user", UserFilter())
        shiroFilter.filters = filters
        log.debug("shiro初始化完成，filter数量为:{}", shiroFilter.filters.size)
        return shiroFilter
    }

    @Bean("dynamicPermissionService")
    @DependsOn("shiroFilter")
    fun filterChainDefinitionsFactory(jdbcPermissionDao: JdbcPermissionDao): DynamicPermissionServiceImpl{
        val filter=context?.getBean(ShiroFilterFactoryBean::class.java)
        val service =DynamicPermissionServiceImpl()
        service.shiroFilter=filter?.`object` as? AbstractShiroFilter
        service.dynamicPermissionDao=jdbcPermissionDao
        service.definitions="/**/favicon.ico=anon\n" +
                "/logout = logout"
        return service
    }
}