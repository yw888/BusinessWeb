package com.neuedu.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CategoryMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.IProductService;
import com.neuedu.utils.DateUtil;
import com.neuedu.utils.PropertiesUtils;
import com.neuedu.vo.ProductDetailVO;
import com.neuedu.vo.ProductListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public ServerResponse saveOrUpdate(Product product) {
        if(product == null){
            return ServerResponse.createByError("参数错误！");
        }
        //sub_images 1.jpg,2.jpg,3.jpg
        String subImages = product.getSubImage();
        if(subImages != null && !subImages.equals("")){
            String[] subImageArray = subImages.split(",");
            if(subImageArray != null && subImageArray.length > 0){
                //为商品主图赋值
                product.setMainImage(subImageArray[0]);
            }
        }
        //判断是添加商品，还是更新商品
        Integer productId = product.getId();
        if(productId == null){//添加商品
            int result = productMapper.insert(product);
            if(result > 0){
                return ServerResponse.createBySussess("商品添加成功！");
            }else{
                return ServerResponse.createByError("商品添加失败！");
            }
        }else{
            int result = productMapper.updateByPrimaryKey(product);
            if(result > 0){
                return ServerResponse.createBySussess("商品更新成功！");
            }else{
                return ServerResponse.createByError("商品更新失败！");
            }
        }
    }

    @Override
    public ServerResponse setSaleStatus(Integer productId, Integer status) {

        if(productId == null){
            return ServerResponse.createByError("商品id必须传递");
        }

        if(status == null){
            return ServerResponse.createByError("商品状态信息必须传递");
        }

        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int result = productMapper.updateByPrimaryKeySelective(product);
        if(result > 0){
            return ServerResponse.createBySussess("修改产品状态成功！");
        }
        return ServerResponse.createByError("修改产品状态失败");
    }

    @Override
    public ServerResponse findProductByPage(Integer pageNo, Integer pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        List<Product> productList = productMapper.selectAll();
        List<ProductListVO> productListVOS = new ArrayList<>();
        for(Product product : productList){
            ProductListVO productListVO = new ProductListVO();
            productListVO.setId(product.getId());
            productListVO.setCategoryId(product.getCategoryId());
            productListVO.setName(product.getName());
            productListVO.setMainImage(product.getMainImage());
            productListVO.setSubtitle(product.getSubtitle());
            productListVO.setStatus(product.getStatus());
            productListVO.setPrice(product.getPrice());
            productListVOS.add(productListVO);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVOS);

        return ServerResponse.createBySussess("成功", pageInfo);
    }

    @Override
    public ServerResponse findProductDetail(Integer productId) {
        if(productId == null){
            ServerResponse.createByError("商品id必须传递");
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if(product != null){
            ProductDetailVO productDetailVO = new ProductDetailVO();
            productDetailVO.setId(product.getId());
            productDetailVO.setCategoryId(product.getCategoryId());
            productDetailVO.setName(product.getName());
            productDetailVO.setSubtitle(product.getSubtitle());
            productDetailVO.setMainImage(product.getMainImage());
            productDetailVO.setSubImages(product.getSubImage());
            productDetailVO.setDetail(product.getDetail());
            productDetailVO.setPrice(product.getPrice());
            productDetailVO.setStock(product.getStock());
            productDetailVO.setStatus(product.getStatus());

            Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
            if(category == null){
                productDetailVO.setParentCategoryId(0);
            }else {
                productDetailVO.setParentCategoryId(category.getParentId());
            }

            //imageHost
            productDetailVO.setImageHost((String) PropertiesUtils.getProperty("imagehost"));
            productDetailVO.setCreateTime(DateUtil.dateToString(product.getCreateTime()));
            productDetailVO.setUpdateTime(DateUtil.dateToString(product.getUpdateTime()));

            return ServerResponse.createBySussess("成功", productDetailVO);
        }
        return ServerResponse.createByError("商品不存在！");
    }
}
