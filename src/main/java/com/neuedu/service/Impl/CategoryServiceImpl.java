package com.neuedu.service.Impl;

import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse addCategory(int parentId, String categoryName) {

        if(categoryName == null || categoryName == ""){
            return ServerResponse.createByError("类别名称不能为空!");
        }
        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);
        int result = categoryMapper.insert(category);
        if(result > 0){
            return ServerResponse.createBySussess("添加成功");
        }else {
            return ServerResponse.createByError("添加失败！");
        }

    }

    @Override
    public ServerResponse getCategory(int categoryId) {
        List<Category> categoryList = categoryMapper.findChildCategoryByCategoryId(categoryId);
        return ServerResponse.createBySussess("成功", categoryList);
    }

    @Override
    public ServerResponse set_category_name(Integer categoryId, String categoryName) {

        if(categoryId == null || categoryName == null ){
            return ServerResponse.createByError("参数错误！");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int result = categoryMapper.updateByPrimaryKey(category);
        if(result > 0){
            return ServerResponse.createBySussess("修改成功！");
        }else{
            return ServerResponse.createByError("修改失败！");
        }

    }
}
