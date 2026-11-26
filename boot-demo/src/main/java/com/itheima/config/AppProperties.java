package com.itheima.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * prefix = "app"  →  自动绑定配置文件里 app.xxx 的所有值
 */
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name;

    private String version;

    private Author author = new Author();

    @Data
    public static class Author {
        private String name;
        private String email;
    }
}
