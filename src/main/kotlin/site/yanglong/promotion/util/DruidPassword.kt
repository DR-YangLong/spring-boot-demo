package site.yanglong.promotion.util

import com.alibaba.druid.filter.config.ConfigTools

/**
 * package: site.yanglong.promotion.util <br/>
 * functional describe:生成mysql密文密码和密钥。
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/9/25
 */
class DruidPassword

fun main(args: Array<String>){
    val password = "你的密码"
    val arr = ConfigTools.genKeyPair(512)
    val privateKey = arr[0]
    val publicKey = arr[1]
    println("私钥:" + privateKey)
    println("公钥:" + publicKey)
    val passwordEntry = ConfigTools.encrypt(arr[0], password)
    println("密文密码:" + passwordEntry)
    println("原明文密码:" + ConfigTools.decrypt(publicKey, passwordEntry))
}
