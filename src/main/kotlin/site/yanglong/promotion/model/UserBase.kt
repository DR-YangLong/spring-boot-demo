package site.yanglong.promotion.model

import com.baomidou.mybatisplus.activerecord.Model
import com.baomidou.mybatisplus.annotations.*
import com.baomidou.mybatisplus.enums.FieldFill
import java.io.Serializable
import java.util.*

@TableName("user_base")
class UserBase : Model<UserBase>(), Serializable {
    @TableId
    var userId: Long? = null

    var userMobile: String? = null

    var userName: String? = null

    @TableField(fill = FieldFill.INSERT)
    var userPwd: String? = null

    var appToken: String? = null

    @TableField//逻辑删除
    @TableLogic//逻辑删除，公用可抽象到抽象父类
    var userStatus: String? = null

    @TableField(fill = FieldFill.UPDATE)//公用可抽象到抽象父类
    var modifyDate: Date? = null

    @Version//公用可抽象到抽象父类
    var lockVersion: Long?=null

    companion object {
        private const val serialVersionUID = 7584846556783479679L
    }

    override fun pkVal(): Serializable {
        return this.userId!!
    }
}