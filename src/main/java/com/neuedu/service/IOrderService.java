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
}

