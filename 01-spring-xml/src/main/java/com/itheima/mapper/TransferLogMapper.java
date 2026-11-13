package com.itheima.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface TransferLogMapper {

    @Insert("insert into transfer_log (from_id, to_id, money, create_time) " +
            "values (#{fromId}, #{toId}, #{money}, #{createTime})")
    void insert(@Param("fromId") Integer fromId,
                @Param("toId") Integer toId,
                @Param("money") BigDecimal money,
                @Param("createTime") LocalDateTime createTime);
}
