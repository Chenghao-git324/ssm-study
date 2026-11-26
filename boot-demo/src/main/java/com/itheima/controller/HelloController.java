package com.itheima.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello boot";
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        return Map.of(
                "framework", "SpringBoot 3.5.10",
                "java", System.getProperty("java.version"),
                "port", "8090"
        );
    }
}
