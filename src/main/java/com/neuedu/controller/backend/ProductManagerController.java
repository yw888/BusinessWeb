package com.neuedu.controller.backend;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.User;
import com.neuedu.service.IProductService;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/product")
public class ProductManagerController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

    /**
     * 新增或更新产品
     * @return
     */
    @RequestMapping(value = "/save.do")
    public ServerResponse saveOrUpdate(Product product, HttpSession session){
        return productService.saveOrUpdate(product);
    }

    /**
     * 产品上下架
     * @param productId
     * @param status
     * @param session
     * @return
     */
    @RequestMapping(value = "/set_sale_status.do")
    public ServerResponse setSaleStatus(Integer productId, Integer status, HttpSession session){
        return productService.setSaleStatus(productId, status);
    }

    /**
     * 分页列表查询
     * @param pageNo
     * @param pageSize
     * @param session
     * @return
     */
    @RequestMapping(value = "/list.do")
    public ServerResponse list(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                               @RequestParam(required = false, defaultValue = "10") Integer pageSize, HttpSession session){
        return productService.findProductByPage(pageNo, pageSize);
    }

    /**
     * 获取产品详情
     * @param productId
     * @param session
     * @return
     */
    @RequestMapping(value = "/detail.do")
    public ServerResponse detail(Integer productId, HttpSession session){
        return productService.findProductDetail(productId);
    }

    /**
     * 产品搜索
     * @param productId
     * @param productName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/search.do")
    public ServerResponse search(@RequestParam(required = false) Integer productId,
                                 @RequestParam(required = false) String productName,
                                 @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                 @RequestParam(required = false, defaultValue = "10")Integer pageSize, HttpSession session){
        //用户是否登录
        return this.productService.searchProductByIdOrName(productId, productName, pageNo, pageSize);
    }
}
