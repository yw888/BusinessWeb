package com.neuedu.service;

import com.neuedu.common.ServerResponse;

public interface IOrderService {
    /**
     * 创建订单
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse createOrder(Integer userId, Integer shippingId);

    /**
     * 获取商品信息
     * @param userId
     * @return
     */
    ServerResponse getOrderCartProduct(Integer userId);

    /**
     * 前台-订单列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ServerResponse list(Integer userId, Integer pageNo, Integer pageSize);

    /**
     * 前台获取订单明细
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse detail(Integer userId, Long orderNo);
}

