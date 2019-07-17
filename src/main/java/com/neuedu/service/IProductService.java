package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;

public interface IProductService {
    /**
     * 更新或添加商品
     */
    ServerResponse saveOrUpdate(Product product);

    /**
     * 产品上下架
     * @param productId
     * @param status
     * @return
     */
    ServerResponse setSaleStatus(Integer productId, Integer status);

    /**
     * 分页查询商品
     * @param pageNo
     * @param pageSize
     * @return
     */
    ServerResponse findProductByPage(Integer pageNo, Integer pageSize);

    /**
     * 获取产品详情
     * @param productId
     * @return
     */
    ServerResponse findProductDetail(Integer productId);
}
