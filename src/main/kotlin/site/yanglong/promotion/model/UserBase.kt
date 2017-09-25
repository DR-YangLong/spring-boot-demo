package site.yanglong.promotion.model

import com.baomidou.mybatisplus.activerecord.Model
import com.baomidou.mybatisplus.annotations.TableName
import java.io.Serializable

@TableName("user_base")
class UserBase : Model<UserBase>(), Serializable {
    var userId: Long? = null

    var userMobile: String? = null

    var userName: String? = null

    var userPwd: String? = null

    var appToken: String? = null

    var userStatus: String? = null

    companion object {
        private const val serialVersionUID = 7584846556783479679L
    }

    override fun pkVal(): Serializable {
        return this.userId!!
    }
}