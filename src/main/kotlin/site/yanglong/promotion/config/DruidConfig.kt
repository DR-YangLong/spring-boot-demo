package site.yanglong.promotion.config

import com.alibaba.druid.pool.DruidDataSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.sql.SQLException
import javax.sql.DataSource

/**
 * package: site.yanglong.promotion.config <br/>
 * functional describe: druid configuration
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/12
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
class DruidConfig {
    private val logger: Logger = LoggerFactory.getLogger(DruidConfig::class.java)
    var url: String? = null
    var username: String? = null
    var password: String? = null
    var driverClassName: String? = null
    var initialSize: Int = 0
    var minIdle: Int = 0
    var maxActive: Int = 0
    var maxWait: Long = 0
    var timeBetweenEvictionRunsMillis: Int = 0
    var minEvictableIdleTimeMillis: Int = 0
    var validationQuery: String? = null
    var testWhileIdle: Boolean = false
    var testOnBorrow: Boolean = false
    var testOnReturn: Boolean = false
    var poolPreparedStatements: Boolean = false
    var maxPoolPreparedStatementPerConnectionSize: Int = 0
    var filters: String? = null
    var connectionProperties: String? = null

    @Bean     //声明其为Bean实例
    @Primary //在同样的DataSource中，首先使用被标注的DataSource
    fun dataSource(): DataSource {
        val datasource = DruidDataSource()
        datasource.url = url
        datasource.username = username
        datasource.password = password
        datasource.driverClassName = driverClassName
        //configuration
        datasource.initialSize = initialSize
        datasource.minIdle = minIdle
        datasource.maxActive = maxActive
        datasource.maxWait = maxWait
        datasource.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis.toLong()
        datasource.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis.toLong()
        datasource.validationQuery = validationQuery
        datasource.isTestWhileIdle = testWhileIdle
        datasource.isTestOnBorrow = testOnBorrow
        datasource.isTestOnReturn = testOnReturn
        datasource.isPoolPreparedStatements = poolPreparedStatements
        datasource.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize
        try {
            datasource.setFilters(filters)
        } catch (e: SQLException) {
            logger.error("druid configuration initialization filter", e)
        }
        datasource.setConnectionProperties(connectionProperties)
        return datasource
    }
}