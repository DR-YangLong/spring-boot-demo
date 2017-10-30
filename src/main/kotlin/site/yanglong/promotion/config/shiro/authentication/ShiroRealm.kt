/*
        Copyright  DR.YangLong

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
package site.yanglong.promotion.config.shiro.authentication

import org.apache.shiro.authc.*
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection

/**
 * functional describe:查询用户授权及认证信息
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/5/14 13:38
 */
class ShiroRealm : AuthorizingRealm() {
    /**
     * 获取认证和授权信息的服务接口
     */
    var realmService: RealmService? = null

    /**
     * 是否使用权限
     */
    var isEnablePerms = false
    /**
     * 是否使用角色
     */
    var isEnableRoles = true
    /**
     * 用户身份唯一标识在Map中的key
     */
    var identity_in_map_key = "id"
    /**
     * 密码在map中的key
     */
    var password_in_map_key = "pwd"
    /**
     * 用户状态在map中的key
     */
    var user_status_in_map_key = "status"
    /**
     * 账号禁用时匹配的字符串的值
     */
    var user_status_forbidden = "1"
    /**
     * 账号锁定时匹配的字符串的值
     */
    var user_status_locked = "2"
    /**
     * 角色在map中的KEY
     */
    var roles_in_map_key = "roles"
    /**
     * 权限在map中的KEY
     */
    var perms_in_map_key = "perms"

    /**
     * 获取认证信息，会通过realmDao或取用户的id和密码【此2项是必须的】，以及用户状态【非必须，当获取为NULL时不会中断认证】
     *
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(authcToken: AuthenticationToken): AuthenticationInfo {
        val token = authcToken as UsernamePasswordToken
        val info = realmService!!.getUserUniqueIdentityAndPassword(token.username)
        val flag = info == null || info.isEmpty() || info[identity_in_map_key] == null || info[password_in_map_key] == null
        if (!flag) {
            val status = info[user_status_in_map_key]
            if (status != null) {
                val userStatus = status.toString()
                if (user_status_forbidden == userStatus) {//禁用账号
                    throw DisabledAccountException("DisabledAccountException")
                }
                if (user_status_locked == userStatus) {//账号锁定
                    throw LockedAccountException("LockedAccountException")
                }
            }
            return SimpleAuthenticationInfo(info[identity_in_map_key], info[password_in_map_key], name)
        } else {
            throw UnknownAccountException("UnknownAccountException")//没找到帐号;
        }
    }

    /**
     * 获取授权信息
     *
     * @param principals
     * @return
     */
    override fun doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo? {
        if (!principals.isEmpty && principals.fromRealm(name).isNotEmpty()) {
            val id = principals.fromRealm(name).iterator().next()
            if (id != null) {
                val info = SimpleAuthorizationInfo()
                if (isEnableRoles && isEnablePerms) {
                    val rolesAndPerms = realmService!!.getUserRolesAndPerms(id)
                    val roles = rolesAndPerms[roles_in_map_key]
                    val perms = rolesAndPerms[perms_in_map_key]
                    if (roles != null && !roles.isEmpty()) {
                        info.addRoles(roles)
                    }
                    if (perms != null && !perms.isEmpty()) {
                        info.addStringPermissions(perms)
                    }
                } else if (isEnableRoles && !isEnablePerms) {
                    val roles = realmService?.getRoles(id)
                    if (null!=roles && !roles.isEmpty()) {
                        info.addRoles(roles)
                    }
                } else if (isEnablePerms && !isEnableRoles) {
                    val perms = realmService?.getPermissions(id)
                    if (null!=perms && !perms.isEmpty()) {
                        info.addStringPermissions(perms)
                    }
                }
                return info
            } else {
                return null
            }
        } else
            return null
    }


    /**
     * 获取认证信息在缓存中的键值
     * 此方法可以自定义认证信息在缓存中的键值，可以使用唯一标识和信息对应的方式
     * 此处的principals为此类中doGetAuthenticationInfo方法设置的principal，
     * 如果此realm前面还有realm，将有多个principal
     *
     * @param principals 凭证
     * @return key
     */
    override fun getAuthorizationCacheKey(principals: PrincipalCollection): Any? {
        if (!principals.isEmpty && principals.fromRealm(name).isNotEmpty()) {
            val id = principals.fromRealm(name).iterator().next()
            if (id != null) {
                return "DRZ_" + id
            }
        }
        return null
    }

    /**
     * 此方法登录时不会调用，且使用缓存时才调用此方法，默认使用传入的token键值作为缓存key，
     * 此方法在登出时调用，返回值必须和
     * [.getAuthenticationCacheKey]相同
     *
     *
     * [org.apache.shiro.realm.AuthenticatingRealm.getAuthenticationCacheKey]
     *
     * @param principals
     * @return
     */
    override fun getAuthenticationCacheKey(principals: PrincipalCollection): Any? {
        if (!principals.isEmpty && principals.fromRealm(name).isNotEmpty()) {
            val id = principals.fromRealm(name).iterator().next()
            if (id != null) {
                return "DRC_" + id
            }
        }
        return null
    }

    /**
     * 认证缓存key，登录时调用，默认使用token值，使用缓存时才调用此方法
     * [org.apache.shiro.realm.AuthenticatingRealm.getAuthenticationCacheKey]
     *
     * @param token token
     * @return key
     */
    override fun getAuthenticationCacheKey(token: AuthenticationToken?): Any? {
        val simpleToken = token as UsernamePasswordToken?
        val id = realmService!!.getUniqueIdentity(simpleToken!!.username.toLowerCase())
        return if (id != null) {
            "DRC_" + id
        } else null
    }
}
