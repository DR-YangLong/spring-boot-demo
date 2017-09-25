package site.yanglong.promotion.vo

/**
 * package: site.yanglong.promotion.vo <br/>
 * functional describe:message and code relations
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/21
 */
enum class Status(val code: Int, val msg: String) {
    SUCCESS(200, "success"),
    NORMAL_FAILURE(201, "failure"),
    USER_NOT_EXISTS(400, "user not exists"),
    TOKEN_INVALID(401, "token invalid"),
    PARAM_ERROR(402, "parameters error"),
    PRINCIPAL_INVALID(402, "userName or password invalid"),
    NO_MORE_DATA(500, "no more data")
}