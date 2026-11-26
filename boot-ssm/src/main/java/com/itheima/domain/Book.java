package com.itheima.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("book")
public class Book {

    /** 主键，由数据库自增生成 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private Double price;

    /** 数据库没有这一列，让 MP 忽略它 */
    @TableField(exist = false)
    private Address address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;

    /** 逻辑删除标记：查询自动过滤 deleted=0，删除变成 update */
    @TableLogic
    private Integer deleted;

    /** 乐观锁版本号：更新时自动加 where version=? 并 set version=version+1 */
    @Version
    private Integer version;
}
