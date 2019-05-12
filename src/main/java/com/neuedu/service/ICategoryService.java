package com.neuedu.service;

import com.neuedu.common.ServerResponse;

public interface ICategoryService {

    /**
     * 添加类别
     * @param parentId
     * @param categoryName
     * @return
     */
    ServerResponse addCategory(int parentId, String categoryName);

    /**
     * 获取子节点（不包含后代节点）
     */
    ServerResponse getCategory(int categoryId);

    /**
     * 修改类别名称
     */
    ServerResponse set_category_name(Integer categoryId, String categoryName);

    /**
     * 递归获取本节点及后代节点
     * @param categoryId
     * @return
     */
    ServerResponse get_deep_category(Integer categoryId);

}
