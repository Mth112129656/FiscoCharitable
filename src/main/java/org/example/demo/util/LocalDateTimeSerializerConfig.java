package org.example.demo.util;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class LocalDateTimeSerializerConfig {

    /**
     * 注入配置文件格式化的格式
     *
     * @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
     * private String pattern;
     */
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 注册响应json格式的LocalDateTime日期格式序列化器,指定格式
     */
    @Bean
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * 注册json请求方式的LocalDateTime反序列化器,指定格式
     */
    @Bean
    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
    }

    /**
     * 格式化日期类型,响应对应格式化日期字符串
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            //返回json格式,前端序列化为字符串
            builder.serializerByType(LocalDateTime.class, localDateTimeSerializer());
            //从json对象日期字符串反序列化为日期对象
            builder.deserializerByType(LocalDateTime.class, localDateTimeDeserializer());
        };
    }
}

