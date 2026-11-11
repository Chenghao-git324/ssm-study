package com.itheima.factory;

import com.itheima.dao.BookDao;
import com.itheima.dao.impl.BookDaoImpl;
import org.springframework.beans.factory.FactoryBean;

public class BookDaoFactoryBean implements FactoryBean<BookDao> {

    @Override
    public BookDao getObject() {
        System.out.println("FactoryBean：创建 BookDao");
        return new BookDaoImpl();
    }

    @Override
    public Class<?> getObjectType() {
        return BookDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

