package com.itheima;

import com.itheima.dao.BookDao;
import com.itheima.collection.CollectionBean;
import com.itheima.service.BookService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class App {

    public static void main(String[] args) {
        // 1. 启动 Spring 容器，读取 beans.xml
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");


        // 2. 从容器里按 id 取 Bean
        BookService bookService = (BookService) ctx.getBean("bookService");

        BookDao bookDao1 = (BookDao) ctx.getBean("bookDao");
        BookDao bookDao2 = (BookDao) ctx.getBean("bookDao");
        System.out.println("两次拿到的是同一个对象吗？" + (bookDao1 == bookDao2));


        CollectionBean collectionBean = (CollectionBean) ctx.getBean("collectionBean");
        collectionBean.print();


        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("数据库连接成功：" + conn);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        // 3. 调用
        bookService.save();
        ctx.close();

    }

}

