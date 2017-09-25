package site.yanglong.promotion.vo

import java.io.Serializable

/**
 * package: site.yanglong.promotion.vo <br/>
 * functional describe:view model wrapper
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/21
 */
class StatusResult<T> : Serializable {
    var code: Int? = null
    var message: String? = null
    var data: T? = null
    fun setStatus(status: Status): Unit {
        code = status.code
        message = status.msg
    }
}