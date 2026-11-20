package com.itheima.controller;

import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.itheima.domain.Book;

import java.util.Arrays;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/book/save")
    public String save() {
        System.out.println("BookController.save() 执行了");
        System.out.println("controller 里的 service hashCode = " + bookService.hashCode());
        bookService.save();
        return "save success";
    }

    @GetMapping("/book/query")
    public String query(String name, Integer age) {
        System.out.println("name = " + name + ", age = " + age);
        return "name = " + name + ", age = " + age;
    }

    @GetMapping("/book/savePojo")
    public String savePojo(Book book) {
        System.out.println("book = " + book);
        return book.toString();
    }

    @GetMapping("/book/arrayParam")
    public String arrayParam(Integer[] ids) {
        System.out.println("ids = " + java.util.Arrays.toString(ids));
        return java.util.Arrays.toString(ids);
    }

    @GetMapping("/book/listParam")
    public String listParam(@RequestParam List<Integer> ids) {
        System.out.println("ids = " + ids);
        return ids.toString();
    }

    @PostMapping("/book/json")
    public String json(@RequestBody Book book) {
        System.out.println("json book = " + book);
        return book.toString();
    }

    @GetMapping("/book/get")
    public Book getBook() {
        Book book = new Book();
        book.setId(9);
        book.setName("三体");
        book.setPrice(45.5);
        return book;
    }

    @GetMapping("/book/list")
    public List<Book> list() {
        Book b1 = new Book();
        b1.setId(1);
        b1.setName("三体");
        b1.setPrice(45.5);

        Book b2 = new Book();
        b2.setId(2);
        b2.setName("活着");
        b2.setPrice(39.0);

        return Arrays.asList(b1, b2);
    }

}
