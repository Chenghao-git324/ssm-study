package com.itheima.dao.impl;

import com.itheima.dao.BookDao;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Repository

public class BookDaoImpl implements BookDao {

    public BookDaoImpl() {
        System.out.println("BookDaoImpl 构造方法");
    }

    @PostConstruct
    public void init() {
        System.out.println("BookDao init ...");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("BookDao destroy ...");
    }

    @Override
    public void save() {
        System.out.println("book dao save ...");
    }
}


