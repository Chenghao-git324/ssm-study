package com.itheima;

import com.itheima.dao.BookDao;

import com.itheima.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        // 1. 启动 Spring 容器，读取 beans.xml
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");


        // 2. 从容器里按 id 取 Bean
        BookService bookService = (BookService) ctx.getBean("bookService");

        BookDao bookDao1 = (BookDao) ctx.getBean("bookDao");
        BookDao bookDao2 = (BookDao) ctx.getBean("bookDao");
        System.out.println("两次拿到的是同一个对象吗？" + (bookDao1 == bookDao2));


       // Object product = ctx.getBean("bookDao");
        //Object factory = ctx.getBean("&bookDao");
       // System.out.println("bookDao 实际类型：" + product.getClass().getName());
        //System.out.println("&bookDao 实际类型：" + factory.getClass().getName());

        // 3. 调用
        bookService.save();
        ctx.close();

    }

}

