package site.yanglong.promotion.mapper

import org.apache.ibatis.annotations.Mapper
import site.yanglong.promotion.model.Resources
import com.baomidou.mybatisplus.mapper.BaseMapper
import site.yanglong.promotion.model.dto.UriPermissions

/**
 *
 *
 * Mapper 接口
 *
 *
 * @author Dr.YangLong
 * @since 2017-11-01
 */
@Mapper
interface ResourcesMapper : BaseMapper<Resources>{
    /**
     * 这个方法不准，只是示例返回string的collection映射写法。
     */
    fun selectDefinitions():List<UriPermissions>

    fun selectRoles():List<UriPermissions>

    fun selectPerms():List<UriPermissions>
}