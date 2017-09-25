package site.yanglong.promotion.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CharacterEncodingFilter
import org.springframework.web.filter.CorsFilter

/**
 * package: site.yanglong.promotion.config <br/>
 * functional describe:Web configuration,encoding,CORS access.
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/12
 */
@Configuration
class WebConfig {
    @Bean
    fun EncodingFilter(): CharacterEncodingFilter {
        val characterEncodingFilter = CharacterEncodingFilter()
        characterEncodingFilter.encoding = "UTF-8"
        characterEncodingFilter.setForceEncoding(true)
        return characterEncodingFilter
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        //allowed host
        config.allowedOrigins = listOf("*")
        config.maxAge = 3600
        config.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        //对某个url配置跨域 the allowed uri
        source.registerCorsConfiguration("/api/**", config)
        source.registerCorsConfiguration("/druid/**", config)
        return CorsFilter(source)
    }


}