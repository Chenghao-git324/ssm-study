package com.itheima.factory;


import com.itheima.dao.BookDao;
import com.itheima.dao.impl.BookDaoImpl;

public class BookDaoFactory {
    public BookDao getBookDao() {
        System.out.println("实例工厂：创建 BookDao");
        return new BookDaoImpl();
    }
}
