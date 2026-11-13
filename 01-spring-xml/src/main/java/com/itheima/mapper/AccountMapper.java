package com.itheima.mapper;

import com.itheima.entity.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

public interface AccountMapper {

    @Update("update account set money = money - #{money} where id = #{fromId}")
        // ↑ 转出：减钱
    void outMoney(@Param("fromId") Integer fromId, @Param("money") BigDecimal money);

    @Update("update account set money = money + #{money} where id = #{toId}")
        // ↑ 转入：加钱
    void inMoney(@Param("toId") Integer toId, @Param("money") BigDecimal money);

    @Select("select * from account where id = #{id}")
    Account findById(Integer id);
    // ↑ 查余额，用来验证转账结果
}

