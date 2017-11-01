package site.yanglong.promotion.service.impl

import site.yanglong.promotion.model.RolesResources
import site.yanglong.promotion.mapper.RolesResourcesMapper
import site.yanglong.promotion.service.RolesResourcesService
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
class RolesResourcesServiceImpl : ServiceImpl<RolesResourcesMapper, RolesResources>(), RolesResourcesService
