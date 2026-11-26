package com.itheima.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.Book;
import org.apache.ibatis.annotations.Mapper;

/**
 * 继承 BaseMapper 之后，CRUD 方法全部自带：
 * insert / deleteById / updateById / selectById / selectList / selectPage ...
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
