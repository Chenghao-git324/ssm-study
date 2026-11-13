package com.itheima;

import com.itheima.config.SpringConfig;
import com.itheima.dao.BookDao;
import com.itheima.service.BookService;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;

public class AnnotationApp {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringConfig.class);

        BookService bookService = ctx.getBean(BookService.class);
        BookDao bookDao1 = ctx.getBean(BookDao.class);
        BookDao bookDao2 = ctx.getBean(BookDao.class);
        System.out.println("bookDao1 == bookDao2 ? " + (bookDao1 == bookDao2));

        DataSource dataSource = ctx.getBean(DataSource.class);
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("注解版数据源连接成功：" + conn);
        }


        bookService.save();

        System.out.println(bookService.findName(1));

        bookService.login("admin", "   123456   ");
// ↑ 故意在密码前后加空格
        System.out.println("bookService 的 class：" + bookService.getClass().getName());
        System.out.println("是不是 AOP 代理：" + AopUtils.isAopProxy(bookService));
        System.out.println("是不是 JDK 代理：" + AopUtils.isJdkDynamicProxy(bookService));
        System.out.println("是不是 CGLIB 代理：" + AopUtils.isCglibProxy(bookService));

        ctx.close();
    }
}
