package site.yanglong.promotion.config.shiro

import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * package: site.yanglong.promotion.config <br/>
 * functional describe: shiro生命周期配置，如果和其他配置在一个类中，会导致自动注入属性配置类失败
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/10/31
 */
@Configuration
class ShiroLifeCycleConfig {

    /**
     * shiro会话生命周期管理
     *
     * @return
     */
    @Bean(name = arrayOf("lifecycleBeanPostProcessor"))
    fun lifecycleBeanPostProcessor(): LifecycleBeanPostProcessor {
        return LifecycleBeanPostProcessor()
    }
}