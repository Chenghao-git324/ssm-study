package com.itheima.service;

import java.math.BigDecimal;

public interface AccountService {
    void transfer(Integer fromId, Integer toId, BigDecimal money);
    // ↑ 从 fromId 转 money 到 toId

}
