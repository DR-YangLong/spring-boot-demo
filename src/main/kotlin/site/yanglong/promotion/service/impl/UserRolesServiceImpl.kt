package site.yanglong.promotion.service.impl

import site.yanglong.promotion.model.UserRoles
import site.yanglong.promotion.mapper.UserRolesMapper
import site.yanglong.promotion.service.UserRolesService
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
class UserRolesServiceImpl : ServiceImpl<UserRolesMapper, UserRoles>(), UserRolesService
