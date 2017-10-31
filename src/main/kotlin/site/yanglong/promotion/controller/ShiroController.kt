package site.yanglong.promotion.controller

import org.apache.shiro.authc.*
import org.apache.shiro.web.util.WebUtils
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.yanglong.promotion.util.ShiroUtils
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 * functional describe:shiro测试controller，
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/10/28
 */
@RestController
@RequestMapping("shiro")
class ShiroController {

    /**
     * shiro login method
     */
    @GetMapping("login")
    fun login(request: HttpServletRequest): String {
        var result = "login failure!"
        val subject = ShiroUtils.getSubject()
        if (subject.isAuthenticated) {
            result = "already login!"
        } else {
            //获取用户输入参数
            var username = WebUtils.getCleanParam(request, "username")
            if (!StringUtils.isEmpty(username)) {
                var password = WebUtils.getCleanParam(request, "password")
                if (!StringUtils.isEmpty(password)) {
                    var rememberMe = WebUtils.getCleanParam(request, "rememberMe")
                    var loginToken = UsernamePasswordToken(username, password, Objects.equals("1", rememberMe))
                    try {
                        subject.login(loginToken)
                        result = "[" + Date() + "]login success!"
                    } catch (e: Exception) {
                        when (e) {
                            is UnknownAccountException -> result = "no this account!"
                            is DisabledAccountException -> result = "this account is disabled!"
                            is LockedAccountException -> result = "this account is locked!"
                            is CredentialsException -> result = "password error!"
                        }
                    }

                } else {
                    result = "please input password!"
                }

            } else {
                result = "please input username and password!"
            }
        }
        return result
    }

    @GetMapping("user")
    fun userRole(): String {
        return "user test!"
    }

    @GetMapping("admin")
    fun adminRole(): String {
        return "admin test!"
    }

    @GetMapping("perm")
    fun userPerms(): String {
        return "perm test!"
    }

    @GetMapping("anon")
    fun anon(): String {
        return "anon test!"
    }

    @GetMapping("logout")
    fun logout(): String {
        ShiroUtils.logout()
        return "logout success!"
    }
}
