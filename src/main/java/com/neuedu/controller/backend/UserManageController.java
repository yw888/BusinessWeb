package com.neuedu.controller.backend;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/user")
public class UserManageController {
    @Autowired
    IUserService userService;

    @RequestMapping(value = "/login.do")
    public ServerResponse login(String username, String password,HttpSession session){
        ServerResponse serverResponse = userService.login(username, password);
        User user = (User) serverResponse.getData();
        if(user.getRole() != Const.USERROLE.ADMINUSER){
            //防止横向越权
            return serverResponse.createByError("普通用户无法登录后台！");
        }else{
            session.setAttribute(Const.CURRENT_USER, user);
        }
        return serverResponse;
    }

    /**
     * 分页获取数据
     * @param pageNo
     * @param pageSize
     * @param session
     * @return
     */
    @RequestMapping(value = "/list.do")
    public ServerResponse findUserByPageNo(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                           @RequestParam(required = false, defaultValue = "10") int pageSize,
                                           HttpSession session){
        return userService.selectUserByPageNo(pageNo, pageSize);
    }




}
