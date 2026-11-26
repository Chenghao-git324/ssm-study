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

    /** 条件查询：三个参数都可以为 null（为空就不参与条件） */
    List<Book> search(String name, Double minPrice, Double maxPrice);

    /** 统计价格大于等于 minPrice 的条数 */
    long countByMinPrice(Double minPrice);

}
