package com.itheima.controller;

import com.itheima.config.AppProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class ConfigController {

    private final AppProperties appProperties;

    /** 对比用：@Value 只能取单个值 */
    @Value("${app.name}")
    private String appName;

    public ConfigController(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @GetMapping("/config")
    public Map<String, Object> config() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("viaValue", appName);
        result.put("viaProperties", appProperties);
        return result;
    }
}
