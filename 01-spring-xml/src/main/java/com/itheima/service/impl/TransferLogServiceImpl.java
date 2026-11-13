package com.itheima.service.impl;

import com.itheima.mapper.TransferLogMapper;
import com.itheima.service.TransferLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransferLogServiceImpl implements TransferLogService {

    @Autowired
    private TransferLogMapper transferLogMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    // ↑ REQUIRES_NEW：挂起外层事务，自己开一个全新的事务
    //   外层回滚时，它已经提交了，不受影响
    @Override
    public void log(Integer fromId, Integer toId, BigDecimal money) {
        transferLogMapper.insert(fromId, toId, money, LocalDateTime.now());
    }
}
