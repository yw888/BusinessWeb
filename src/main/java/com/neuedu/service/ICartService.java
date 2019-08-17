package com.neuedu.service;


import com.neuedu.common.ServerResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICartService {

    /**
     * 向购物车添加商品
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse add(Integer userId, Integer productId, Integer count);

    /**
     * 获取购物车列表
     * @param userId
     * @return
     */
    ServerResponse list(Integer userId);

    /**
     * 更新购物车产品
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse update(Integer userId, Integer productId, Integer count);

    /**
     * 移除购物车中的商品
     * @param userId
     * @param productIds
     * @return
     */
    ServerResponse delete(Integer userId, String productIds);

    /**
     * 购物车的选中和取消选中操作
     * @param userId
     * @param checked 1: 选中，0 取消选中
     * @return
     */
    ServerResponse selectOrUnselect(Integer userId,Integer productId, Integer checked);

    /**
     * 获取购物车中商品数量
     * @param userId
     * @return
     */
    ServerResponse<Integer> get_cart_product_count(Integer userId);

}
