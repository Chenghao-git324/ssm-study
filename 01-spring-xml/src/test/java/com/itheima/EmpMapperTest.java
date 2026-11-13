package com.itheima;

import com.itheima.config.MyBatisConfig;
import com.itheima.entity.Emp;
import com.itheima.mapper.EmpMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
// ↑ 告诉 JUnit：用 Spring 的扩展来跑测试
//   它会启动 Spring 容器、处理 @Autowired
@ContextConfiguration(classes = MyBatisConfig.class)
// ↑ 用哪个配置类启动容器
public class EmpMapperTest {

    @Autowired
    private EmpMapper empMapper;
    // ↑ 这个接口没有实现类，但 @MapperScan 已经生成了代理对象
    //   Spring 按类型把它注入进来

    @Test
    // ↑ 注意 import 是 org.junit.jupiter.api.Test（JUnit 5）
    //   如果写成 org.junit.Test 就是 JUnit 4，跑不起来
    public void testFindById() {
        Emp emp = empMapper.findById(1);
        // ↑ 调用代理对象 → MyBatis 执行 SQL → 把结果映射成 Emp
        System.out.println(emp);
    }
}

