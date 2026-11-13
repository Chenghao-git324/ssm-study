package com.itheima.entity;

import java.math.BigDecimal;

public class Emp {

    private Integer id;
    // ↑ 用 Integer 不用 int：数据库里可能是 null，int 装不了 null

    private String name;
    private Integer age;
    private String gender;
    // ↑ 表里 gender 是 char(1)，存 '1'/'2'，用 String 接最省事

    private BigDecimal salary;
    // ↑ 钱用 BigDecimal，不要用 double（double 有精度丢失）

    private Integer deptId;
    // ↑ 表里列名是 dept_id（下划线），这里写 deptId（驼峰）
    //   下一步 MyBatis 会配置「下划线 → 驼峰」的自动映射

    // getter / setter 全部生成（IDEA：Alt+Insert → Getter and Setter）

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", deptId=" + deptId +
                '}';
    }
}
