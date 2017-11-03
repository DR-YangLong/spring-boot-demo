package site.yanglong.promotion.service

import site.yanglong.promotion.model.Resources
import com.baomidou.mybatisplus.service.IService
import site.yanglong.promotion.model.dto.UriPermissions

/**
 *
 *
 * 服务类
 *
 *
 * @author Dr.YangLong
 * @since 2017-11-01
 */
interface ResourcesService : IService<Resources>{
    fun listDefinitions():List<UriPermissions>

    fun definitionsMap():LinkedHashMap<String,String>?
}
