package site.yanglong.promotion.model

import com.baomidou.mybatisplus.activerecord.Model
import com.baomidou.mybatisplus.annotations.TableField
import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.annotations.TableLogic
import com.baomidou.mybatisplus.annotations.TableName
import java.io.Serializable

@TableName("user_base")
class UserBase : Model<UserBase>(), Serializable {
    @TableId
    var userId: Long? = null

    var userMobile: String? = null

    var userName: String? = null

    var userPwd: String? = null

    var appToken: String? = null

    @TableField//逻辑删除
    @TableLogic//逻辑删除
    var userStatus: String? = null

    companion object {
        private const val serialVersionUID = 7584846556783479679L
    }

    override fun pkVal(): Serializable {
        return this.userId!!
    }
}