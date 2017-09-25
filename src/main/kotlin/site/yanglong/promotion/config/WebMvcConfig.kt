package site.yanglong.promotion.config

import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * package: site.yanglong.promotion.config <br/>
 * functional describe: mvc configuration
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2017/7/13
 */
@Configuration
class WebMvcConfig: WebMvcConfigurerAdapter() {

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>?) {
        super.configureMessageConverters(converters)
        val fastConverter = FastJsonHttpMessageConverter()
        val fastJsonConfig = FastJsonConfig()
        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.IgnoreErrorGetter)
        fastConverter.fastJsonConfig = fastJsonConfig
        converters!!.add(fastConverter)
    }
}