package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;

import java.util.Set;

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

    /**
     * 后台产品搜索
     * @param productId
     * @param productName
     * @param pageNo
     * @param pageSize
     * @return
     */
    ServerResponse searchProductByIdOrName(Integer productId, String productName, Integer pageNo, Integer pageSize);

    /**
     * 前台商品搜索接口
     * @param keyword
     * @param categoryId
     * @param pageNo
     * @param pageSize
     * @param orderBy
     * @return
     */
    ServerResponse searchProduct(String keyword, Integer categoryId, Integer pageNo, Integer pageSize, String orderBy);

    /**
     * 前台-获取商品详情
     * @param productId
     * @return
     */
    ServerResponse getProductDetail(Integer productId);

    }
