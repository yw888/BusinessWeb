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

    /**
     * 前台取消订单
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse cancel(Integer userId, Long orderNo);

    /**
     * 后台-按订单号查询
     * @param orderNo
     * @return
     */
    ServerResponse search(Long orderNo);

    /**
     * 后台-订单发货
     * @param orderNo
     * @return
     */
    ServerResponse send(Long orderNo);

    /**
     * 定时关闭订单
     * 关闭在closeOrderTime之前的订单
     * @param closeOrderTime
     */
    void closeOrder(String closeOrderTime);

}

