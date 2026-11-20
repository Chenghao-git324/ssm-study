package com.itheima.service.impl;

import com.itheima.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    public BookServiceImpl() {
        // 每创建一次就打印一次，用来观察创建了几份
        System.out.println("BookServiceImpl 被创建了，hashCode = " + this.hashCode());
    }

    @Override
    public void save() {
        System.out.println("BookServiceImpl.save() 执行了");
    }
}
