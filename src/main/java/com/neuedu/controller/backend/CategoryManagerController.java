package com.neuedu.controller.backend;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category")
public class CategoryManagerController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICategoryService categoryService;


    /**
     * 增加类别
     * @param parentId
     * @param categoryName
     * @param session
     * @return
     */
    @RequestMapping(value = "/add_category.do")
    public ServerResponse add_category(@RequestParam(required = false, defaultValue = "0") int parentId,
                                       String categoryName, HttpSession session){
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
            return categoryService.addCategory(parentId, categoryName);
        }else{
            return ServerResponse.createByError("用户无权操作!");
        }
    }

    /**
     * 获取子节点（不包含后代节点）
     */
    @RequestMapping(value = "/get_category.do")
    public ServerResponse get_category(int categoryId, HttpSession session){

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
            return categoryService.getCategory(categoryId);
        }else{
            return ServerResponse.createByError("用户无权操作!");
        }
    }

    /**
     * 修改类别名称
     */
    @RequestMapping(value = "/set_category_name.do")
    public ServerResponse set_category_name(Integer categoryId, String categoryName, HttpSession session){

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
            return categoryService.set_category_name(categoryId, categoryName);
        }else{
            return ServerResponse.createByError("用户无权操作!");
        }
    }

    /**
     * 递归获取后代节点
     * @param categoryId
     * @param session
     * @return
     */
    @RequestMapping(value = "/get_deep_category.do")
    public ServerResponse get_deep_category(Integer categoryId, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //未登录或登录过期
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        //判断是否是管理员
        ServerResponse serverResponse = userService.checkUserAdmin(user);
        if(serverResponse.isSucces()){
            //管理员
            return categoryService.get_deep_category(categoryId);
        }
        return ServerResponse.createByError("不是管理员,没有权限操作!");
    }

}
