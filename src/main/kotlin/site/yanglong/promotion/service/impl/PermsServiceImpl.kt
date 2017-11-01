package site.yanglong.promotion.service.impl

import site.yanglong.promotion.model.Perms
import site.yanglong.promotion.mapper.PermsMapper
import site.yanglong.promotion.service.PermsService
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
class PermsServiceImpl : ServiceImpl<PermsMapper, Perms>(), PermsService
