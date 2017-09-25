package site.yanglong.promotion.mapper

import com.baomidou.mybatisplus.mapper.BaseMapper
import org.apache.ibatis.annotations.Mapper
import site.yanglong.promotion.model.UserBase

@Mapper
interface UserBaseMapper :BaseMapper<UserBase> {

    fun selectByToken(token: String): UserBase

    fun selectWithToken(): List<UserBase>

    fun deleteToken(token: String): Int

    fun updateAppToken(token: String): Int
}