package com.itheima.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        System.out.println("UserController.hello() 执行了");
        return "hello springmvc";
    }
}
