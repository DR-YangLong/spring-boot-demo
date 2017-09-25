package site.yanglong.promotion.service

import com.baomidou.mybatisplus.service.IService
import site.yanglong.promotion.model.UserBase

/**
 * package: site.yanglong.promotion.service <br/>
 * functional describe:user service interface,the user operate,add,update,delete,find
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/13
 */
interface UserService :IService<UserBase>{
    fun findByName(name:String,page:Int,size:Int):List<UserBase>
}