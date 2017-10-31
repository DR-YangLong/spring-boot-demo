package site.yanglong.promotion.config.shiro.dynamic

import org.apache.shiro.config.Ini
import org.apache.shiro.util.CollectionUtils
import org.apache.shiro.util.StringUtils
import org.apache.shiro.web.config.IniFilterChainResolverFactory
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver
import org.apache.shiro.web.servlet.AbstractShiroFilter
import org.slf4j.LoggerFactory
import java.util.*
import javax.annotation.PostConstruct


class DynamicPermissionServiceImpl : DynamicPermissionService {
    private val logger = LoggerFactory.getLogger(DynamicPermissionServiceImpl::class.java)
    var shiroFilter: AbstractShiroFilter? = null
    var dynamicPermissionDao: DynamicPermissionDao? = null
    var definitions = ""

    @PostConstruct
    @Synchronized override fun init() {
        logger.debug("初始化filter加权限信息，在servlet的init方法之前。")
        reloadPermission()
        logger.debug("初始化filter权限信息成功，开始执行servlet的init方法。")
    }

    @Synchronized override fun reloadPermission() {
        logger.debug("reload资源权限配置开始！")
        try {
            val perms = generateSection()
            val manager = getFilterChainManager()
            manager.filterChains.clear()
            addToChain(manager, perms)
        } catch (e: Exception) {
            logger.error("reload资源权限配置发生错误！", e)
        }

        logger.debug("reload资源权限配置结束！")
    }

    @Throws(Exception::class)
    private fun getFilterChainManager(): DefaultFilterChainManager {
        // 获取过滤管理器
        val filterChainResolver = shiroFilter!!
                .filterChainResolver as PathMatchingFilterChainResolver
        return filterChainResolver.filterChainManager as DefaultFilterChainManager
    }

    @Throws(Exception::class)
    private fun addToChain(manager: DefaultFilterChainManager, definitions: Map<String, String>?) {
        if (definitions == null || CollectionUtils.isEmpty(definitions)) {
            return
        }
        //移除/**的过滤器链，防止新加的权限不起作用。
        manager.filterChains.remove("/**")
        for ((url, value) in definitions) {
            val chainDefinition = value.trim { it <= ' ' }.replace(" ", "")
            manager.createChain(url, chainDefinition)
        }
    }

    @Synchronized override fun updatePermission(newDefinitions: LinkedHashMap<String, String>) {
        logger.debug("更新资源配置开始！")
        try {
            // 获取和清空初始权限配置
            val manager = getFilterChainManager()
            addToChain(manager, newDefinitions)
            newDefinitions.put("/**", "anon")
            logger.debug("更新资源权限配置成功。")
        } catch (e: Exception) {
            logger.error("更新资源权限配置发生错误!", e)
        }

    }

    /**
     * 将静态配置的资源与要添加的动态资源整合在一起，生成shiro使用的权限map
     * {@see org.apache.shiro.spring.web.ShiroFilterFactoryBean#setFilterChainDefinitions(String)}
     *
     * @return Section
     */
    private fun generateSection(): Map<String, String>? {
        var section: Ini.Section? = null
        if (StringUtils.hasLength(definitions)) {
            val ini = Ini()
            ini.load(definitions) // 加载资源文件节点串定义的初始化权限信息
            section = ini.getSection(IniFilterChainResolverFactory.URLS) // 使用默认节点
            if (CollectionUtils.isEmpty(section)) {
                section = ini.getSection(Ini.DEFAULT_SECTION_NAME)//如不存在默认节点切割,则使用空字符转换
            }
        }
        /**
         * 加载非初始化定义的权限信息
         */
        val permissionMap = loadDynamicPermission()
        if (!CollectionUtils.isEmpty(permissionMap)) {
            if (CollectionUtils.isEmpty(section)) {
                logger.debug("*********获取初始静态配置URL权限映射失败*********")
                return permissionMap
            } else {
                section?.putAll(permissionMap)
            }
        }
        return section
    }

    /**
     * 加载动态权限资源配置,map<ant url,comma-delimited chain definition>
     * @return map
     * */
    private fun loadDynamicPermission(): Map<String, String> {
        var map = dynamicPermissionDao!!.findDefinitionsMap()
        if (CollectionUtils.isEmpty(map)) {
            map = LinkedHashMap()
            logger.error("**********没有进行动态权限配置！**********")
        }
        logger.debug("*************自定义权限配置完成*************")
        return map
    }

}