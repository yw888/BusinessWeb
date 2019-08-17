package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;

public interface IShippingService {
    /**
     * 添加地址
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse add(Integer userId, Shipping shipping);

    /**
     * 删除地址
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse delete(Integer userId, Integer shippingId);

    /**
     * 更新地址
     * @param userId
     * @param shipping
     * @return
     */
    ServerResponse update(Integer userId, Shipping shipping);

    /**
     * 查询
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse select(Integer userId, Integer shippingId);

    /**
     * 分页查询地址列表
     * @param userId
     * @param pageNo
     * @param pageSize
     * @return
     */
    ServerResponse list(Integer userId, Integer pageNo, Integer pageSize);
}
