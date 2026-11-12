package com.itheima;

import com.itheima.dao.BookDao;
import com.itheima.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GetBeanApp {

    public static void main(String[] args) {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("beans-container.xml");

        // 1. 按 id 取：返回 Object，要强转
        BookService s1 = (BookService) ctx.getBean("bookService");

        // 2. 按类型取：不用强转
        BookService s2 = ctx.getBean(BookService.class);

        // 3. 按 id + 类型取：最安全
        BookService s3 = ctx.getBean("bookService", BookService.class);

        System.out.println("s1 == s2 ? " + (s1 == s2));
        System.out.println("s2 == s3 ? " + (s2 == s3));



    }


}
