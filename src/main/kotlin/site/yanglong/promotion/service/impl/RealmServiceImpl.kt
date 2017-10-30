package site.yanglong.promotion.service.impl

import org.apache.commons.codec.digest.DigestUtils
import org.springframework.stereotype.Service
import site.yanglong.promotion.config.shiro.authentication.RealmService

/**
 * functional describe:实现动态权限的
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/10/20
 */
@Service("realmService")
class RealmServiceImpl : RealmService {

    override fun getUniqueIdentity(userName: String): Any {
        return 1
    }

    override fun getUserPassword(userName: String): Any {
        return DigestUtils.md5Hex("123456")
    }

    override fun getUserUniqueIdentityAndPassword(userName: String): Map<String, Any> {
        return mapOf<String, String>("id" to "1", "pwd" to DigestUtils.md5Hex("123456"), "status" to "0")
    }

    override fun getPermissions(uniqueIdentity: Any): Collection<String> {
        return listOf("a", "b", "c")
    }

    override fun getRoles(uniqueIdentity: Any): Collection<String> {
        return listOf("user", "admin")
    }

    override fun getUserRolesAndPerms(uniqueIdentity: Any): Map<String, Collection<String>> {
        val perms = listOf("a", "b", "c")
        val roles = listOf("user", "admin")
        println()
        return hashMapOf("perms" to perms, "roles" to roles)
    }
}
