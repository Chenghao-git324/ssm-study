package com.itheima.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// ↑ 配置类
@Import(DataSourceConfig.class)
// ↑ 把 DataSourceConfig 引进来，这样下面才能注入 DataSource
@MapperScan("com.itheima.mapper")
// ↑ 扫描这个包下的 Mapper 接口，给每个接口生成代理对象并注册成 Bean
//   Bean 的 id 默认是接口名首字母小写：empMapper
public class MyBatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        // 方法参数 DataSource：Spring 会自动把容器里的 dataSource 传进来

        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        // ↑ 注意：这是 MyBatis 提供的 FactoryBean
        //   它的 getObject() 返回 SqlSessionFactory
        //   你在关 3 学的 FactoryBean，这里见到真身了

        factoryBean.setDataSource(dataSource);
        // ↑ SqlSessionFactory 需要 DataSource 才能连数据库

        org.apache.ibatis.session.Configuration mybatisConfig =
                new org.apache.ibatis.session.Configuration();
        // ↑ 这里有两个 Configuration：
        //   spring 的 @Configuration 注解（上面 import 的）
        //   mybatis 的 Configuration 类（这里要 new 的）
        //   名字撞了，所以 MyBatis 这个写全限定名

        mybatisConfig.setMapUnderscoreToCamelCase(true);
        // ↑ 开启「下划线 → 驼峰」自动映射：
        //   数据库 dept_id  →  Emp.deptId

        factoryBean.setConfiguration(mybatisConfig);
        return factoryBean;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        // ↑ 事务管理器：关 9 的 @Transactional 就靠它
        //   现在先注册，后面直接用
        return new DataSourceTransactionManager(dataSource);
    }
}

