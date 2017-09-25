package site.yanglong.promotion.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.script.DefaultRedisScript
import org.springframework.data.redis.core.script.RedisScript
import org.springframework.scripting.support.ResourceScriptSource
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import site.yanglong.promotion.vo.Status
import site.yanglong.promotion.vo.StatusResult
import java.util.*

/**
 * package: site.yanglong.promotion.controller <br/>
 * functional describe: redis simple example；
 * 演示2种方式使用jedis的script（lua），直接传递字符串和加载lua脚本文件。
 * 未演示使用redis服务端lua脚本的hash码调用。
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/9/21
 */
@RestController
@RequestMapping("redis/bonus")
class RedisController {
    //群红包金额hash表 key
    private val amount_hash_name: String = "bonus:amount"
    //已领取的红包群人数hash表 key
    private val received_hash_name: String = "bonus:num"
    //群人数hash表 key
    private val group_number_hash_name: String = "group:size"

    @Autowired
    private var redisTemplate: StringRedisTemplate? = null

    /**
     * 作为一个示例，生成红包，此处的逻辑是，将群红包放到3个hash中：
     * 一个hash存放groupID->amount，一个hash存放groupID->群里已领取红包人数；
     * 一个存放groupID->群总人数。
     * 每次生成红包即往这3个hash表中插入或更新数据，因而会产生覆盖。
     * 可设计更为复杂的，以红包id为关联的程序。
     */
    @RequestMapping("add")
    fun createBonus(groupId: Long, amount: Double, memberNo: Long): StatusResult<String> {
        val script: RedisScript<Long> = DefaultRedisScript("redis.call(\"hset\",\"" + amount_hash_name + "\",KEYS[1],ARGV[1])\n" +
                "redis.call(\"hset\",\"" + received_hash_name + "\",KEYS[1],0)\n" +
                "redis.call(\"hset\",\"" + group_number_hash_name + "\",KEYS[1],ARGV[2])\n" +
                "return true", Long::class.java)
        val keys: List<String> = listOf(groupId.toString())
        //run lua script for create record
        val success = redisTemplate?.execute(script, keys, amount.toString(), memberNo.toString()) ?: 0
        val result: StatusResult<String> = StatusResult()
        if (success > 0) {
            result.setStatus(Status.SUCCESS)
        } else {
            result.setStatus(Status.NORMAL_FAILURE)
        }
        return result
    }

    /**
     * 领取红包
     */
    @RequestMapping("receive")
    fun receivedBonus(groupId: Long): StatusResult<Double> {
        val result: StatusResult<Double> = StatusResult()
        val balance: String? = redisTemplate?.opsForHash<String, String>()?.get(amount_hash_name, groupId.toString())
        if (null != balance && balance.toDouble() > 0) {
            val script: DefaultRedisScript<String> = DefaultRedisScript()
            script.setScriptSource(ResourceScriptSource(ClassPathResource("receive_bonus.lua")))
            script.resultType = String::class.java
            val amount: String? = redisTemplate?.execute(script, Collections.singletonList(groupId.toString()))
            if (null != amount && !amount.contains("nan")) {
                val k: List<String> = amount.split("_")
                val has_bonus = k[1].toLong()
                result.data = k[0].toDouble()
                result.setStatus(Status.SUCCESS)
                return result
            }
        }
        result.code = Status.NORMAL_FAILURE.code
        result.message = "红包已发完"
        result.data = 0.0
        return result
    }

}