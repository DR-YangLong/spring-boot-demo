package site.yanglong.promotion.service

import com.baomidou.mybatisplus.service.IService
import com.jarvis.cache.annotation.Cache
import site.yanglong.promotion.model.UserBase

/**
 * package: site.yanglong.promotion.service <br/>
 * functional describe:user service interface,the user operate,add,update,delete,find
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/13
 */
interface UserService :IService<UserBase>{
    @Cache(expire = 500,key = "'findUserByNamePage:'+#args[0]+':'+#args[1]+':'+#args[2]",autoload = true,requestTimeout = 18000)
    fun findByName(name:String,page:Int,size:Int):List<UserBase>
}