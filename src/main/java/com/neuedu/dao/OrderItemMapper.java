package com.neuedu.dao;

import com.neuedu.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order_item
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order_item
     *
     * @mbg.generated
     */
    int insert(OrderItem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order_item
     *
     * @mbg.generated
     */
    OrderItem selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order_item
     *
     * @mbg.generated
     */
    List<OrderItem> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order_item
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(OrderItem record);

    /**
     * 批量插入订单明细
     * @param orderItemList
     * @return
     */
    int batchInsertOrderItem(List<OrderItem> orderItemList);

    /**
     * 根据userId和orderNo查询订单明细
     * @param userId
     * @param orderNo
     * @return
     */
    List<OrderItem> selectAllByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);
}