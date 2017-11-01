package site.yanglong.promotion.service.impl

import site.yanglong.promotion.model.PermsResources
import site.yanglong.promotion.mapper.PermsResourcesMapper
import site.yanglong.promotion.service.PermsResourcesService
import com.baomidou.mybatisplus.service.impl.ServiceImpl
import org.springframework.stereotype.Service

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
class PermsResourcesServiceImpl : ServiceImpl<PermsResourcesMapper, PermsResources>(), PermsResourcesService
