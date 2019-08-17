package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;
import com.neuedu.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    /**
     * 向购物车中添加商品
     * @param session
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping("/add.do")
    public ServerResponse add(HttpSession session, Integer productId, Integer count){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //未登录或登录过期
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return cartService.add(user.getId(), productId, count);
    }

    /**
     * 获取购物车列表
     * @param session
     * @return
     */
    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //未登录或登录过期
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return cartService.list(user.getId());
    }

    /**
     * 更新购物车中的产品
     * @param session
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping("/update.do")
    public ServerResponse update(HttpSession session, Integer productId, Integer count){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //未登录或登录过期
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return cartService.update(user.getId(), productId, count);
    }

    /**
     * 购物车中移除商品
     * @param session
     * @param productIds
     * @return
     */
    @RequestMapping("/delete.do")
    public ServerResponse delete(HttpSession session, String productIds){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //未登录或登录过期
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return cartService.delete(user.getId(), productIds);
    }

    /**
     * 全选
     * @param session
     * @return
     */
    @RequestMapping("/select_all.do")
    public ServerResponse select_all(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //未登录或登录过期
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return cartService.selectOrUnselect(user.getId(), null, Const.Cart.CHECKED);

    }


    /**
     * 全不选
     * @param session
     * @return
     */
    @RequestMapping("/un_select_all.do")
    public ServerResponse un_select_all(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //未登录或登录过期
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return cartService.selectOrUnselect(user.getId(), null, Const.Cart.UNCHECKED);
    }

    /**
     * 单选
     * @param session
     * @return
     */
    @RequestMapping("/select.do")
    public ServerResponse select(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //未登录或登录过期
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return cartService.selectOrUnselect(user.getId(), productId, Const.Cart.CHECKED);

    }

    /**
     * 取消单选
     * @param session
     * @return
     */
    @RequestMapping("/un_select.do")
    public ServerResponse un_select(HttpSession session, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //未登录或登录过期
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }

        return cartService.selectOrUnselect(user.getId(), productId, Const.Cart.UNCHECKED);
    }

    /**
     * 获取购物车中商品数量
     * @param session
     * @return
     */
    @RequestMapping("/get_cart_product_count.do")
    public ServerResponse<Integer> get_cart_product_count(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //未登录或登录过期
            return ServerResponse.createBySussess(0);
        }

        return this.cartService.get_cart_product_count(user.getId());
    }




}
