package site.yanglong.promotion.model

import java.io.Serializable

import com.baomidou.mybatisplus.enums.IdType
import com.baomidou.mybatisplus.annotations.TableId
import com.baomidou.mybatisplus.activerecord.Model
import com.baomidou.mybatisplus.annotations.TableField
import com.baomidou.mybatisplus.annotations.TableLogic

/**
 *
 *
 *
 *
 *
 * @author Dr.YangLong
 * @since 2017-11-01
 */
class Roles : Model<Roles>() {

    /**
     * 角色id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 角色字符串标识
     */
    var identify: String? = null
    /**
     * 名称
     */
    var name: String? = null
    /**
     * 备注说明
     */
    var remark: String? = null
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
        return "Roles{" +
                ", id=" + id +
                ", identify=" + identify +
                ", name=" + name +
                ", remark=" + remark +
                ", enable=" + enable +
                "}"
    }

    companion object {

        private val serialVersionUID = 1L
    }
}
