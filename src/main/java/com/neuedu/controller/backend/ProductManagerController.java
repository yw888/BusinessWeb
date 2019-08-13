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
        //用户是否登录
        User cuser = (User)session.getAttribute(Const.CURRENT_USER);
        if(cuser == null){
            //需要登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        //是否有管理员权限
        ServerResponse serverResponse = userService.checkUserAdmin(cuser);
        if(serverResponse.isSucces()){
            //有管理员权限
            return productService.saveOrUpdate(product);
        }else{
            return ServerResponse.createByError("用户无权操作!");
        }
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
        //用户是否登录
        User cuser = (User)session.getAttribute(Const.CURRENT_USER);
        if(cuser == null){
            //需要登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        //是否有管理员权限
        ServerResponse serverResponse = userService.checkUserAdmin(cuser);
        if(serverResponse.isSucces()){
            //有管理员权限
            return productService.setSaleStatus(productId, status);
        }else{
            return ServerResponse.createByError("用户无权操作!");
        }
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
        //用户是否登录
        User cuser = (User)session.getAttribute(Const.CURRENT_USER);
        if(cuser == null){
            //需要登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        //是否有管理员权限
        ServerResponse serverResponse = userService.checkUserAdmin(cuser);
        if(serverResponse.isSucces()){
            //有管理员权限
            return productService.findProductByPage(pageNo, pageSize);
        }else{
            return ServerResponse.createByError("用户无权操作!");
        }
    }

    /**
     * 获取产品详情
     * @param productId
     * @param session
     * @return
     */
    @RequestMapping(value = "/detail.do")
    public ServerResponse detail(Integer productId, HttpSession session){
        //用户是否登录
        User cuser = (User)session.getAttribute(Const.CURRENT_USER);
        if(cuser == null){
            //需要登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        //是否有管理员权限
        ServerResponse serverResponse = userService.checkUserAdmin(cuser);
        if(serverResponse.isSucces()){
            //有管理员权限
            return productService.findProductDetail(productId);
        }else{
            return ServerResponse.createByError("用户无权操作!");
        }
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
        User cuser = (User)session.getAttribute(Const.CURRENT_USER);
        if(cuser == null){
            //需要登录
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        //是否有管理员权限
        ServerResponse serverResponse = userService.checkUserAdmin(cuser);
        if(serverResponse.isSucces()){
            //有管理员权限
            return this.productService.searchProductByIdOrName(productId, productName, pageNo, pageSize);
        }else{
            return ServerResponse.createByError("用户无权操作!");
        }
    }
}
