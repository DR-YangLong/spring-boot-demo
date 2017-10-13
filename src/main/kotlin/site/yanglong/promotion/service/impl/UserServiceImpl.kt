package site.yanglong.promotion.service.impl

import com.baomidou.mybatisplus.mapper.EntityWrapper
import com.baomidou.mybatisplus.plugins.Page
import com.baomidou.mybatisplus.service.impl.ServiceImpl
import org.springframework.stereotype.Service
import site.yanglong.promotion.mapper.UserBaseMapper
import site.yanglong.promotion.model.UserBase
import site.yanglong.promotion.service.UserService

/**
 * package: site.yanglong.promotion.service.impl <br/>
 * functional describe:implement user-service interface
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/13
 */
@Service
class UserServiceImpl : ServiceImpl<UserBaseMapper, UserBase>(), UserService {

    override fun findByName(name: String, page: Int, size: Int): List<UserBase> {
        val wrapper: EntityWrapper<UserBase> = EntityWrapper<UserBase>()
        wrapper.like("userName", name)
        return baseMapper.selectPage(Page<UserBase>(page, size), wrapper)
    }

    //乐观锁，不用事务
    override fun updateByOptimisticLock(user: UserBase): Boolean {
        if (user.userId == null) return false
        val userBase: UserBase? = baseMapper.selectById(user.userId)
        if (null != userBase) {
            val version: Long? = userBase.lockVersion
            user.lockVersion = version
            return baseMapper.updateById(user) > 0
        }
        return false
    }
}