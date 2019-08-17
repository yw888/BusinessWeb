package com.neuedu.dao;

import com.neuedu.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbg.generated
     */
    int insert(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbg.generated
     */
    Shipping selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbg.generated
     */
    List<Shipping> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_shipping
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Shipping record);

    /**
     * 删除地址
     * @param userId
     * @param shippingId
     * @return
     */
    int deleteByPrimaryKeyAndUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    Shipping selectByPrimaryKeyAndUserId(@Param("userId") Integer userId, @Param("shippingId") Integer shippingId);

    /**
     * 根据用户查询所有的地址
     * @param userId
     * @return
     */
    List<Shipping> selectAllByUserId(Integer userId);


}