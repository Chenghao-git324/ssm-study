package com.itheima.controller;

import com.itheima.domain.PageResult;
import com.itheima.domain.Result;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.itheima.domain.Book;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;


    @GetMapping("/books")
    public Result<List<Book>> list() {
        return Result.success(bookService.list());
    }

    @PostMapping("/books")
    public Result<Void> add(@RequestBody Book book) {
        bookService.add(book);
        return Result.success();
    }

    @GetMapping("/books/{id}")
    public Result<Book> getById(@PathVariable Integer id) {
        return Result.success(bookService.getById(id));
    }

    @PutMapping("/books")
    public Result<Void> update(@RequestBody Book book) {
        bookService.update(book);
        return Result.success();
    }

    @DeleteMapping("/books/{id}")
    public Result<Void> delete(@PathVariable Integer id) {
        bookService.delete(id);
        return Result.success();
    }

    @GetMapping("/books/page")
    public Result<PageResult<Book>> page(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "2") int size) {
        return Result.success(bookService.page(page, size));
    }


}
