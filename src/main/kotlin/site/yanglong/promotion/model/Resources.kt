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
class Resources : Model<Resources>() {

    /**
     * 资源id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 资源标识
     */
    var uri: String? = null
    /**
     * 资源名称
     */
    var name: String? = null
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
        return "Resources{" +
                ", id=" + id +
                ", uri=" + uri +
                ", name=" + name +
                ", enable=" + enable +
                "}"
    }

    companion object {

        private val serialVersionUID = 1L
    }
}
