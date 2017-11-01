package site.yanglong.promotion.service.impl

import site.yanglong.promotion.model.Resources
import site.yanglong.promotion.mapper.ResourcesMapper
import site.yanglong.promotion.service.ResourcesService
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
class ResourcesServiceImpl : ServiceImpl<ResourcesMapper, Resources>(), ResourcesService
