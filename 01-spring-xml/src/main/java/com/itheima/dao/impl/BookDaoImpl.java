package com.itheima.dao.impl;

import com.itheima.dao.BookDao;

public class BookDaoImpl implements BookDao {

    public BookDaoImpl() {
        System.out.println("BookDaoImpl 构造方法");
    }

    public void init() {
        System.out.println("BookDao init ...");
    }

    public void destroy() {
        System.out.println("BookDao destroy ...");
    }

    @Override
    public void save() {
        System.out.println("book dao save ...");
    }
}

