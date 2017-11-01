package site.yanglong.promotion.service.impl

import site.yanglong.promotion.model.UserPerms
import site.yanglong.promotion.mapper.UserPermsMapper
import site.yanglong.promotion.service.UserPermsService
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
class UserPermsServiceImpl : ServiceImpl<UserPermsMapper, UserPerms>(), UserPermsService
