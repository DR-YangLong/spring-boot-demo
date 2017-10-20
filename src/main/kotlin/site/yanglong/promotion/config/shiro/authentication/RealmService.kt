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

/**
 * functional describe:realm获取用户认证信息，授权信息的service接口
 * 此方法为Reaml提供信息，MAP中key需要自定义或使用默认{@see ShiroRealm}
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0 2015/5/14 13:42
 */
interface RealmService {
    /**
     * 获取用户唯一标识
     * @param userName form表单提交的用户名称
     * @return Object
     */
    fun getUniqueIdentity(userName: String): Any

    /**
     * 获取用户密码
     * @param userName form表单提交的用户名称
     * @return 密码
     */
    fun getUserPassword(userName: String): Any

    /**
     * 获取用户的唯一标识和密码以及用户状态，[ShiroRealm]，此方法必须实现
     * @return map
     */
    fun getUserUniqueIdentityAndPassword(userName: String): Map<String, Any>

    /**
     * 获取用户权限信息
     * @param uniqueIdentity 用户唯一标识[.getUniqueIdentity]
     * @return
     */
    fun getPermissions(uniqueIdentity: Any): Collection<String>

    /**
     * 获取用户角色信息
     * @param uniqueIdentity 用户唯一标识[.getUniqueIdentity]
     * @return
     */
    fun getRoles(uniqueIdentity: Any): Collection<String>

    /**
     * 获取用户的角色和权限
     * @return map
     */
    fun getUserRolesAndPerms(uniqueIdentity: Any): Map<String, Collection<String>>
}
