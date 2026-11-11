package com.itheima;

import com.itheima.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        // 1. 启动 Spring 容器，读取 beans.xml
        ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

        // 2. 从容器里按 id 取 Bean
        BookService bookService = (BookService) ctx.getBean("bookService");

        // 3. 调用
        bookService.save();
    }
}

