package com.itheima.entity;

import java.math.BigDecimal;

public class Account {

    private Integer id;
    private String name;
    private BigDecimal money;
    // ↑ 钱依然用 BigDecimal，不用 double

    // getter / setter（Alt+Insert 生成）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
// toString（也生成一下，方便打印）
}

