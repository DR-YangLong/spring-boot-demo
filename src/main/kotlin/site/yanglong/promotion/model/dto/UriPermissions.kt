package site.yanglong.promotion.model.dto

/**
 * functional describe:uri对应的角色和权限信息
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/11/2
 */
class UriPermissions {
    var id: Long? = null
    var uri: String? = null
    var roles: ArrayList<String>? = null
    var perms: ArrayList<String>? = null
}