package site.yanglong.promotion.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import site.yanglong.promotion.config.shiro.dynamic.JdbcPermissionDao
import site.yanglong.promotion.service.ResourcesService
import java.util.*

/**
 * functional describe:基于数据库实现的动态权限dao
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/10/20
 */
@Component
class JdbcPermissionDaoImpl : JdbcPermissionDao {

    @Autowired
    private val resourceService: ResourcesService?=null

    override fun generateDefinitions(): LinkedHashMap<String, String> {
        return resourceService?.definitionsMap()?: linkedMapOf()
    }

    override fun findDefinitionsMap(): Map<String, String> {
        return this.generateDefinitions()
    }
}
