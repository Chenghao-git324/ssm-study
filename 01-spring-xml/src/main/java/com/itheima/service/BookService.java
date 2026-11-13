package com.itheima.service;

public interface BookService {

    void save();

    String findName(Integer id);

    String login(String username, String password);

}
