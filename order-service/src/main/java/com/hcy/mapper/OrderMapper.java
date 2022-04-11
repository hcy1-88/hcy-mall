package com.hcy.mapper;

import com.hcy.dto.OrderForUser;
import com.hcy.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hcy
 * @since 2022-02-10
 */
public interface OrderMapper extends BaseMapper<Order> {
    List<OrderForUser> findOrderByUid(@Param("uid") String uid);
}
