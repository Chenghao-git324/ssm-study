package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.domain.Book;
import com.itheima.domain.PageResult;
import com.itheima.exception.BusinessException;
import com.itheima.mapper.BookMapper;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return bookMapper.selectList(null);          // select * from book
    }

    @Override
    public Book getById(Integer id) {
        Book book = bookMapper.selectById(id);       // where id = ?
        if (book == null) {
            throw new BusinessException("id 不存在");
        }
        return book;
    }

    @Override
    public void add(Book book) {
        bookMapper.insert(book);                     // insert，自增 id 自动回填
    }

    @Override
    public void update(Book book) {
        // 影响行数为 0 有两种可能：id 不存在，或版本号已被别人改过
        if (bookMapper.updateById(book) == 0) {
            throw new BusinessException("更新失败：id 不存在或数据已被他人修改");
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
        Page<Book> pageParam = new Page<>(page, size);
        Page<Book> result = bookMapper.selectPage(pageParam, null);
        return new PageResult<>(result.getTotal(), result.getRecords());
    }

    @Override
    public List<Book> search(String name, Double minPrice, Double maxPrice) {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();

        // 第一个参数是"要不要加这个条件"，为 false 时整条条件被忽略
        wrapper.like(name != null && !name.isBlank(), Book::getName, name)
               .ge(minPrice != null, Book::getPrice, minPrice)
               .le(maxPrice != null, Book::getPrice, maxPrice)
               .orderByDesc(Book::getPrice);

        return bookMapper.selectList(wrapper);
    }

    @Override
    public long countByMinPrice(Double minPrice) {
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(minPrice != null, Book::getPrice, minPrice);
        return bookMapper.selectCount(wrapper);
    }
}
