package com.itheima.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("emp")                 // 类名 Employee ≠ 表名 emp，用注解修正
public class Employee {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("name")           // 字段 employeeName ≠ 列 name，用注解修正
    private String employeeName;

    private Integer age;

    private String gender;

    private BigDecimal salary;

    @TableField("dept_id")        // 驼峰本来也能自动转，这里显式写出来对照
    private Integer deptId;
}
