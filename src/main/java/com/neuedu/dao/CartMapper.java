package com.neuedu.dao;

import com.neuedu.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbg.generated
     */
    int insert(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbg.generated
     */
    Cart selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbg.generated
     */
    List<Cart> selectAll();

    /**
     * 查询用户的购物信息
     */
    List<Cart> findCartByUserId(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_cart
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Cart record);

    int updateByPrimaryKeyBySelectActive(Cart cart);

    /**
     * 判断购物车是否全选
     * @return 0 代表全选
     */
    int isAllChecked(Integer userId);

    /**
     * 通过productId和userId查询购物车
     * @param productId
     * @param userId
     * @return
     */
    Cart findCartByProductIdAndUserId(@Param("productId") Integer productId, @Param("userId") Integer userId);

    /**
     * 批量删除购物车中的商品
     * @param userId
     * @param productIdList
     * @return
     */
    int deleteByUserIdAndProductIds(@Param("userId") Integer userId, @Param("productIdList") List<String> productIdList);

    /**
     * 选中或全不选中
     * @param userId
     * @param checked
     * @return
     */
    int checkedOrUnCheckedProduct(@Param("userId") Integer userId,
                                  @Param("productId") Integer productId,
                                  @Param("checked") Integer checked);

    /**
     * 查询购物车中商品数量
     * @param userId
     * @return
     */
    int selectCartProductCount(Integer userId);

    List<Cart> selectCheckedCartByUserId(Integer userId);
}