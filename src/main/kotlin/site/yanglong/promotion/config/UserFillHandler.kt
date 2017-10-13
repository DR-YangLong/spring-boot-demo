package site.yanglong.promotion.config

import com.baomidou.mybatisplus.mapper.MetaObjectHandler
import org.apache.ibatis.reflection.MetaObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

/**
 * 自动用户密码填充，密码为null时自动填充为123456
 * 自动更新时间填充，更新时强制设置更新时间
 */
class UserFillHandler : MetaObjectHandler() {
    private val logger: Logger =LoggerFactory.getLogger(UserFillHandler::class.java)
    private val fieldName:String= "userPwd"

    //当插入时，如果密码字段为空，自动填充
    override fun insertFill(metaObject: MetaObject?) {
        logger.debug("=========start insert fill pwd field==============")
        val oldVal:Any?=getFieldValByName(fieldName,metaObject)
        logger.debug(oldVal?.toString()?:"null")
        if (null == oldVal){
            setFieldValByName(fieldName,"123456",metaObject)
        }
    }

    //更新是自动填充
    override fun updateFill(metaObject: MetaObject?) {
        logger.debug("=========start update fill pwd field==============")
        setFieldValByName("modifyDate", Date(),metaObject)
    }
}