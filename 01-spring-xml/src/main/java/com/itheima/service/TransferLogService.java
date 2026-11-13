package com.itheima.service;

import java.math.BigDecimal;

public interface TransferLogService {
    void log(Integer fromId, Integer toId, BigDecimal money);
}
