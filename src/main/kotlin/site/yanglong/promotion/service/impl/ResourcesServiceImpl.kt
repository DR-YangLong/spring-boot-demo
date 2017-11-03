package site.yanglong.promotion.service.impl

import com.baomidou.mybatisplus.service.impl.ServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.CollectionUtils
import site.yanglong.promotion.config.shiro.authentication.ShiroRealm
import site.yanglong.promotion.mapper.ResourcesMapper
import site.yanglong.promotion.model.Resources
import site.yanglong.promotion.model.dto.UriPermissions
import site.yanglong.promotion.service.ResourcesService

/**
 *
 *
 * 服务实现类
 *
 *
 * @author Dr.YangLong
 * @since 2017-11-01
 */
@Service
class ResourcesServiceImpl : ServiceImpl<ResourcesMapper, Resources>(), ResourcesService {
    @Autowired
    val shiroRealm: ShiroRealm? = null

    override fun listDefinitions(): List<UriPermissions> {
        //return baseMapper.selectDefinitions()
        val roles = baseMapper.selectRoles()
        val perms = baseMapper.selectPerms()
        if (roles.isEmpty()) {
            return perms
        }
        if (perms.isEmpty()) {
            return roles
        }
        val roleMap = HashMap<Long?, UriPermissions>(roles.size)
        val permMap = HashMap<Long?, UriPermissions>(perms.size)
        roles.forEach { k -> roleMap.put(k.id, k) }
        perms.forEach { k -> permMap.put(k.id, k) }
        if (roles.size <= perms.size) {
            roleMap.forEach { t, u ->
                //向大的map汇总
                val k = permMap[t]
                if (k != null) {
                    k.roles = u.roles
                } else {
                    permMap.put(t, u)
                }
            }
        } else {
            permMap.forEach { t, u ->
                val k = roleMap[t]
                if (k != null) {
                    k.perms = u.perms
                } else {
                    roleMap.put(t, u)
                }
            }
        }
        if (roleMap.size <= permMap.size) {
            val definitions = ArrayList<UriPermissions>(perms.size)
            permMap.forEach { _, u -> definitions.add(u) }
            return definitions
        }
        val definitions = ArrayList<UriPermissions>(roles.size)
        roleMap.forEach { _, u -> definitions.add(u) }
        return definitions
    }

    override fun definitionsMap(): LinkedHashMap<String, String>? {
        val definitions = listDefinitions()
        if (definitions.isNotEmpty()) {
            val map = LinkedHashMap<String, String>(definitions.size)
            for (i in definitions.indices) {
                val d = definitions[i]
                val build = StringBuilder()
                if (!CollectionUtils.isEmpty(d.roles)) {
                    val roles = d.roles
                    build.append(shiroRealm?.roles_in_map_key).append("[")
                    for (k in roles!!.indices) {
                        if (k == 0) {
                            build.append(roles[k])
                        } else {
                            build.append(",").append(roles[k])
                        }
                    }
                    build.append("]")
                }
                if (!CollectionUtils.isEmpty(d.perms)) {
                    val perms = d.perms
                    if(build.isNotEmpty())build.append(",")
                    build.append(shiroRealm?.perms_in_map_key).append("[")
                    for (k in perms!!.indices) {
                        if (i == 0) {
                            build.append(perms[k])
                        } else {
                            build.append(",").append(perms[k])
                        }
                    }
                    build.append("]")
                }
                val uri = d.uri
                if (uri != null) map.put(uri, build.toString())
            }
            return map
        }
        return null
    }
}
