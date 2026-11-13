package com.itheima;

import com.itheima.config.SpringConfig;
import com.itheima.dao.BookDao;
import com.itheima.service.BookService;
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

        ctx.close();
    }
}
