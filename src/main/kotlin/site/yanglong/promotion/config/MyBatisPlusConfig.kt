package site.yanglong.promotion.config

import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor
import com.baomidou.mybatisplus.plugins.PaginationInterceptor
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * package: site.yanglong.promotion.config <br/>
 * functional describe:mybatis-plus pagination plugin simple config.
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/14
 */
@Configuration
@MapperScan("site.yanglong.promotion.mapper")
class MyBatisPlusConfig {

    /**
     * 分页插件配置
     */
    @Bean
    fun paginationInterceptor(): PaginationInterceptor {
        val paginationInterceptor = PaginationInterceptor()
        return paginationInterceptor
    }


    /**
     * 乐观锁插件配置
     */
    @Bean
    fun optimisticLockerInterceptor(): OptimisticLockerInterceptor {
        val olp = OptimisticLockerInterceptor()
        return olp
    }

}