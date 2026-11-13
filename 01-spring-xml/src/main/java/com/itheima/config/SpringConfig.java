package com.itheima.config;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.itheima")
@Import({DataSourceConfig.class, MyBatisConfig.class})
// ↑ 加上 MyBatisConfig：这样 @MapperScan 才生效，AccountMapper 才能被注入
@EnableAspectJAutoProxy
@EnableTransactionManagement
// ↑ 开启事务管理：让 @Transactional 生效
public class SpringConfig {
}
