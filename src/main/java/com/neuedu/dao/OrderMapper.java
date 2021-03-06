package com.neuedu.dao;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order
     *
     * @mbg.generated
     */
    int insert(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order
     *
     * @mbg.generated
     */
    Order selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order
     *
     * @mbg.generated
     */
    List<Order> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Order record);

    /**
     * 通过用户名查询订单
     * @param userId
     * @return
     */
    List<Order> selectAllByUserId(Integer userId);

    /**
     * 根据用户id和orderNo获取订单
     * @param userId
     * @param orderNo
     * @return
     */
    Order getOrderByUserIdAndOrderNo(@Param("userId") Integer userId, @Param("orderNo") Long orderNo);

    /**
     * 更新
     * @param order
     * @return
     */
    int updateOrderBySelectActive(Order order);

    /**
     * 根据订单号查询
     * @param orderNo
     * @return
     */
    Order selectOrderByOrderNo(Long orderNo);

    /**
     * 查询需要关闭的订单
     * @param closeOrderTime
     * @return
     */
    List<Order> findOrderByCreateTime(String closeOrderTime);

}