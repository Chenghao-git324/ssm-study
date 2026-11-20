package com.itheima.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Integer id;
    private String name;
    private Double price;
    private Address address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;

}
