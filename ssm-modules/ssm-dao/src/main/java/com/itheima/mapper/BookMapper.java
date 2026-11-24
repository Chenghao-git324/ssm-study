package com.itheima.mapper;

import com.itheima.domain.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> findAll();

    Book findById(Integer id);

    int insert(Book book);

    int update(Book book);

    int deleteById(Integer id);
}
