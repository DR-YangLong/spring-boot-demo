package site.yanglong.promotion.config

import com.alibaba.druid.support.http.StatViewServlet
import com.alibaba.druid.support.http.WebStatFilter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.alibaba.druid.filter.config.ConfigTools



/**
 * package: site.yanglong.promotion.config <br/>
 * functional describe:druid monitor
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/12
 */
@Configuration
@ConfigurationProperties(prefix = "druid.stat")
class DruidStatConfig {
    var userName:String?=null
    var password:String?=null
    var resetEnable:String?=null
    var allow:String?=null
    var deny:String?=null

    @Bean
    fun druidStatServlet(): ServletRegistrationBean {
        val servletRegistrationBean = ServletRegistrationBean()
        servletRegistrationBean.setServlet(StatViewServlet())
        servletRegistrationBean.addUrlMappings("/druid/*")
        servletRegistrationBean.initParameters = mapOf("allow" to allow, "deny" to deny,
                "loginUsername" to userName,
                "loginPassword" to password,
                "resetEnable" to resetEnable)
        return servletRegistrationBean
    }

    @Bean
    fun druidStatFilter(): FilterRegistrationBean {
        val filterRegistrationBean = FilterRegistrationBean()
        filterRegistrationBean.filter = WebStatFilter()
        filterRegistrationBean.setName("druidWebStatFilter")
        filterRegistrationBean.urlPatterns = listOf("/druid/**")
        filterRegistrationBean.initParameters = mapOf("exclusions" to "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")
        return filterRegistrationBean
    }
}