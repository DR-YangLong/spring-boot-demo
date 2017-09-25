package site.yanglong.promotion.util

import org.apache.shiro.SecurityUtils
import org.apache.shiro.session.Session
import org.apache.shiro.subject.Subject
import site.yanglong.promotion.model.UserBase

/**
 * package: site.yanglong.promotion.util <br/>
 * functional describe: shiro tools
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/12
 */
object ShiroUtils {

    fun getSession(): Session {
        return SecurityUtils.getSubject().session
    }

    fun getSubject(): Subject {
        return SecurityUtils.getSubject()
    }

    fun getUser(): UserBase? {
        return SecurityUtils.getSubject().principal as? UserBase
    }

    fun getUserId(): Long? {
        return getUser()?.userId
    }

    fun setSessionAttribute(key: Any, value: Any) {
        getSession().setAttribute(key, value)
    }

    fun getSessionAttribute(key: Any): Any {
        return getSession().getAttribute(key)
    }

    fun isLogin(): Boolean {
        return SecurityUtils.getSubject().principal != null
    }

    /**
     * hardcode logout
     */
    fun logout() {
        SecurityUtils.getSubject().logout()
    }

    fun getKaptcha(key: String): String {
        val kaptcha = getSessionAttribute(key).toString()
        getSession().removeAttribute(key)
        return kaptcha
    }
}