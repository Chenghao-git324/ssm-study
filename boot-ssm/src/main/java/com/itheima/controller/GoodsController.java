package com.itheima.controller;

import com.itheima.domain.Goods;
import com.itheima.domain.Result;
import com.itheima.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoodsController {

    @Autowired
    private GoodsMapper goodsMapper;

    @PostMapping("/goods")
    public Result<Goods> add(@RequestBody Goods goods) {
        goodsMapper.insert(goods);       // 插入前 MP 会自动生成 id 并回填
        return Result.success(goods);
    }

    @GetMapping("/goods")
    public Result<List<Goods>> list() {
        return Result.success(goodsMapper.selectList(null));
    }
}
