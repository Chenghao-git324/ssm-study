package com.itheima.service;

import com.itheima.domain.Book;
import com.itheima.domain.PageResult;

import java.util.List;

public interface BookService {

    void save();

    List<Book> list();

    Book getById(Integer id);

    void add(Book book);

    void update(Book book);

    void delete(Integer id);

    PageResult<Book> page(int page, int size);

}
