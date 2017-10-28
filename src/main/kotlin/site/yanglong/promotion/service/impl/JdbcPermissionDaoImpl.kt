package site.yanglong.promotion.service.impl

import org.springframework.stereotype.Repository
import site.yanglong.promotion.config.shiro.dynamic.JdbcPermissionDao
import java.util.*

/**
 * functional describe:基于数据库实现的动态权限dao
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/10/20
 */
@Repository
class JdbcPermissionDaoImpl : JdbcPermissionDao {

    override fun generateDefinitions(): LinkedHashMap<String, String> {
        return linkedMapOf("/shiro/admin" to "roles[admin],perms[a]",
                "/shiro/user" to "roles[user]",
                "/shiro/perm" to "perms[b]")
    }

    override fun findDefinitionsMap(): Map<String, String> {
        return this.generateDefinitions()
    }
}
