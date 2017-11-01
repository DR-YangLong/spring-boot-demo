package site.yanglong.promotion.model

import java.io.Serializable

import com.baomidou.mybatisplus.enums.IdType
import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.activerecord.Model
import com.baomidou.mybatisplus.annotations.TableField
import com.baomidou.mybatisplus.annotations.TableLogic
import com.baomidou.mybatisplus.annotations.TableName

/**
 *
 *
 *
 *
 *
 * @author Dr.YangLong
 * @since 2017-11-01
 */
@TableName("user_perms")
class UserPerms : Model<UserPerms>() {

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 用户id
     */
    var userId: Long? = null
    /**
     * 权限id
     */
    var permId: Long? = null
    /**
     * 是否启用
     */
    @TableField
    @TableLogic
    var enable: String? = null

    override fun pkVal(): Serializable? {
        return this.id
    }

    override fun toString(): String {
        return "UserPerms{" +
                ", id=" + id +
                ", userId=" + userId +
                ", permId=" + permId +
                ", enable=" + enable +
                "}"
    }

    companion object {

        private val serialVersionUID = 1L
    }
}
