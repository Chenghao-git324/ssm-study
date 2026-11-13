package com.itheima.mapper;

import com.itheima.entity.Emp;
import org.apache.ibatis.annotations.Select;

public interface EmpMapper {

    @Select("select * from emp where id = #{id}")
        // ↑ @Select 里写 SQL；#{id} 是占位符，走 PreparedStatement，不是字符串拼接
        //   （用 ${} 拼接会有 SQL 注入风险，后面会专门讲）
    Emp findById(Integer id);
    // ↑ 方法只有声明、没有实现类
    //   实现由 MyBatis 在运行时用「动态代理」生成
}

