package site.yanglong.promotion

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.ApplicationContext

/**
 * package: site.yanglong.promotion <br/>
 * functional describe:主函数，程序入口
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/6
 */
@SpringBootApplication(scanBasePackages = arrayOf("site.yanglong.promotion"))
@EnableCaching
class Application
var context: ApplicationContext?=null

fun main(args: Array<String>) {
    println("***********starting***********")
    context=SpringApplication.run(Application::class.java, *args)
    println("***********started***********")
}