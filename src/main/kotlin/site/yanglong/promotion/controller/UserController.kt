package site.yanglong.promotion.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.CollectionUtils
import org.springframework.web.bind.annotation.*
import site.yanglong.promotion.model.UserBase
import site.yanglong.promotion.service.UserService
import site.yanglong.promotion.vo.Status
import site.yanglong.promotion.vo.StatusResult

/**
 * package: site.yanglong.promotion.controller <br/>
 * functional describe: controller ,user CRUD
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/13
 */
@RestController
@RequestMapping("user")
class UserController {

    @Autowired
    private val userService: UserService? = null

    @PostMapping("add")
    fun addUser(user: UserBase): UserBase {
        userService?.insert(user)
        return user
    }

    @DeleteMapping("del")
    fun delUser(userId: Long): StatusResult<Any> {
        val result: StatusResult<Any> = StatusResult()
        val success: Boolean = userService?.deleteById(userId) ?: false
        if (success) result.setStatus(Status.SUCCESS)
        else result.setStatus(Status.NORMAL_FAILURE)
        return result
    }

    @GetMapping("list")
    fun list(name: String, page: Int, size: Int): StatusResult<Any> {
        val users: List<UserBase>? = userService?.findByName(name, page, size)
        val result: StatusResult<Any> = StatusResult()
        if (CollectionUtils.isEmpty(users)) {
            result.setStatus(Status.NO_MORE_DATA)
        } else {
            result.setStatus(Status.SUCCESS)
            result.data = users
        }
        return result
    }

    @PutMapping("update")
    fun updateUser(user: UserBase): StatusResult<Any> {
        val result: StatusResult<Any> = StatusResult()
        if (null != user.userId) {
            val success: Boolean = userService?.updateById(user) ?: false
            if (success) result.setStatus(Status.SUCCESS)
            else result.setStatus(Status.NORMAL_FAILURE)
        } else {
            result.setStatus(Status.PARAM_ERROR)
        }
        return result
    }
}