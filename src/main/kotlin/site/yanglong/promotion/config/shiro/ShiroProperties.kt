package site.yanglong.promotion.config.shiro

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.util.LinkedHashMap
import kotlin.collections.HashMap

/**
 * package: site.yanglong.promotion.config <br/>
 * functional describe: shiro自定义配置项
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/10/31
 */
@Component
@ConfigurationProperties(prefix = "shiro.config")
class ShiroProperties {
    var loginUrl: String? = null
    var successUrl: String? = null
    var unauthorizedUrl: String? = null
    var cachingEnabled: Boolean = true
    var authenticationCachingEnabled: Boolean = true
    var authenticationCacheName: String = "authentication_cache"
    var authorizationCachingEnabled: Boolean = true
    var authorizationCacheName: String = "authorization_cache"
    var rmCookieName: String = "rememberMe"
    var rmCookiePath: String = "/"
    var rmCookieMaxAge: Int = 604800
    var rmCipherKey: String = "4AvVhmFLUs0KTA3Kprsdag=="
    var cookieIsHttpOnly: Boolean = true
    var sessionCacheName: String = "active_session_cache"
    var sessionCookieName: String = "JID"
    var sessionCookieDomain = "yanglong.com"//注意用域名时必须匹配或者设置跨域
    var sessionCookieMaxAge = -1
    var sessionCookiePath = "/"
    //会话过期时间
    var globalSessionTimeout: Long = 1800000
    //自定义的url，权限验证字符串map
    var definitionMap= LinkedHashMap<String, String>()
    //filter过滤标识和对应filter类
    var filtersMap= HashMap<String, String>()
    //初始化definition字符串
    var definitions="/**/favicon.ico=anon \n /logout=logout"
    /**
     * 是否使用权限
     * {@see site.yanglong.promotion.config.shiro.authentication.ShiroRealm}
     */
    var enablePerms = false
    /**
     * 是否使用角色
     */
    var enableRoles = true
    /**
     * 用户身份唯一标识在Map中的key
     */
    var identityInMapKey = "id"
    /**
     * 密码在map中的key
     */
    var passwordInMapKey = "pwd"
    /**
     * 用户状态在map中的key
     */
    var userStatusInMapKey = "status"
    /**
     * 账号禁用时匹配的字符串的值
     */
    var userStatusForbidden = "1"
    /**
     * 账号锁定时匹配的字符串的值
     */
    var userStatusLocked = "2"
    /**
     * 角色在map中的KEY
     */
    var rolesInMapKey = "roles"
    /**
     * 权限在map中的KEY
     */
    var permsInMapKey = "perms"
}