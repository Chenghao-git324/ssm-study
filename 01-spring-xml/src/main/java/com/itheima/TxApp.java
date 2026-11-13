package com.itheima;

import com.itheima.config.SpringConfig;
import com.itheima.mapper.AccountMapper;
import com.itheima.service.AccountService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

public class TxApp {

    public static void main(String[] args)throws Exception {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(SpringConfig.class);

        AccountService accountService = ctx.getBean(AccountService.class);
        AccountMapper accountMapper = ctx.getBean(AccountMapper.class);

        System.out.println("=== 转账前 ===");
        System.out.println(accountMapper.findById(1));
        System.out.println(accountMapper.findById(2));

        accountService.transfer(1, 2, new BigDecimal("100"));
        // ↑ 从 1 号（张三）转 100 给 2 号（李四）
        //   注意：new BigDecimal("100") 用字符串构造，不要用 new BigDecimal(100.0)

        System.out.println("=== 转账后 ===");
        System.out.println(accountMapper.findById(1));
        System.out.println(accountMapper.findById(2));

        ctx.close();
    }
}
