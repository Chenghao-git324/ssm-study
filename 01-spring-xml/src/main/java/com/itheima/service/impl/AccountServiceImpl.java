package com.itheima.service.impl;

import com.itheima.mapper.AccountMapper;
import com.itheima.service.AccountService;
import com.itheima.service.TransferLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TransferLogService transferLogService;
    // ↑ 调用日志 Service（跨 Bean，REQUIRES_NEW 才会生效）

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void transfer(Integer fromId, Integer toId, BigDecimal money)  {
        accountMapper.outMoney(fromId, money);

        transferLogService.log(fromId, toId, money);
        // ↑ 这一步是「独立事务」：
        //   它先提交了，然后才回到外层事务继续


        accountMapper.inMoney(toId, money);
    }
}
