package com.itheima.service.impl;

import com.itheima.dao.BookDao;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookDao bookDao;

    @Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public void save() {
        System.out.println("book service save ...");
        bookDao.save();

        try {
            Thread.sleep(100);   // ← 模拟业务耗时 100ms，方便看出统计效果
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String findName(Integer id) {
        System.out.println("book service findName ...");
        return "book"+id;
    }

    @Override
    public String login(String username, String password) {
        System.out.println("登录：username=" + username + "，password=[" + password + "]");

        return "login-ok";
    }
}
