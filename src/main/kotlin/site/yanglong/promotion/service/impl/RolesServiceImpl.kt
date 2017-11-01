package site.yanglong.promotion.service.impl

import site.yanglong.promotion.model.Roles
import site.yanglong.promotion.mapper.RolesMapper
import site.yanglong.promotion.service.RolesService
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
class RolesServiceImpl : ServiceImpl<RolesMapper, Roles>(), RolesService
