package com.itheima.service.impl;

import com.itheima.domain.Book;
import com.itheima.domain.PageResult;
import com.itheima.exception.BusinessException;
import com.itheima.mapper.BookMapper;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    public BookServiceImpl() {
        System.out.println("BookServiceImpl 被创建了，hashCode = " + this.hashCode());
    }

    @Override
    public void save() {
        System.out.println("BookServiceImpl.save() 执行了");
    }

    @Override
    public List<Book> list() {
        return bookMapper.findAll();
    }

    @Override
    public void add(Book book) {
        bookMapper.insert(book);
    }

    @Override
    public Book getById(Integer id) {
        Book book = bookMapper.findById(id);
        if (book == null) {
            throw new BusinessException("id 不存在");
        }
        return book;
    }

    @Override
    public void update(Book book) {
        if (bookMapper.update(book) == 0) {
            throw new BusinessException("id 不存在");
        }
    }

    @Override
    public void delete(Integer id) {
        if (bookMapper.deleteById(id) == 0) {
            throw new BusinessException("id 不存在");
        }
    }

    @Override
    public PageResult<Book> page(int page, int size) {
        if (page < 1 || size < 1) {
            throw new BusinessException("分页参数不合法");
        }

        PageHelper.startPage(page, size);          // ① 告诉插件：接下来这条要分页
        List<Book> rows = bookMapper.findAll();    // ② 插件偷偷改写成 limit + 查总数
        PageInfo<Book> info = new PageInfo<>(rows); // ③ 从结果里取出总数

        return new PageResult<>(info.getTotal(), info.getList());
    }

}
