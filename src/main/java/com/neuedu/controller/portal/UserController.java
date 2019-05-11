package com.neuedu.controller.portal;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    IUserService userService;

    /**
     * 登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "/login.do")
    public ServerResponse login(String username, String password, HttpSession session){
        ServerResponse serverResponse = userService.login(username, password);
        if(serverResponse.isSucces()){
            //登录成功
            session.setAttribute(Const.CURRENT_USER,serverResponse.getData());
        }
        return serverResponse;
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    public ServerResponse register(User user){
        return userService.register(user);
    }

    /**
     * 检查用户名/邮箱是否有效
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value="/check_valid.do")
    public ServerResponse checkValid(String str, String type){
        return userService.checkValid(str, type);
    }

    /**
     *  获取登录用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/get_user_info.do")
    public ServerResponse getUser(HttpSession session){
        Object o = session.getAttribute(Const.CURRENT_USER);
        if(o == null){//用户未登录
            return ServerResponse.createByError("用户未登录或已过期！");
        }
        return  ServerResponse.createBySussess("成功！");
    }

    /**
     * 忘记密码-获取密保问题
     * @return
     */
    @RequestMapping(value = "/forget_get_question.do")
    public ServerResponse forget_get_question(String username){
        return userService.forget_get_question(username);
    }

    /**
     * 忘记密码-提交问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "/forget_check_answer.do")
    public ServerResponse forget_answer(String username, String question, String answer){
        return userService.forget_answer(username, question, answer);
    }

    /**
     * 忘记密码-修改密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "/forget_reset_password.do")
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken){
        return userService.forget_reset_password(username, passwordNew, forgetToken);
    }

    /**
     * 登录状态下更新密码
     * @param passwordOld
     * @param passwordNew
     * @param session
     * @return
     */
    @RequestMapping(value = "/reset_password.do")
    public ServerResponse reset_password(String passwordOld, String passwordNew, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            //用户未登录或者登录过期，当前端收到status=10跳转到登录页面
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return userService.reset_password(passwordOld, passwordNew, user);
    }

    /**
     * 登录状态下更新个人信息
     * @param user
     * @param session
     * @return
     */
    @RequestMapping(value = "/update_information.do")
    public ServerResponse update_information(User user, HttpSession session){
        User curtent_user = (User)session.getAttribute(Const.CURRENT_USER);
        if(curtent_user == null){
            //用户未登录或者登录过期，当前端收到status=10跳转到登录页面
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        user.setId(curtent_user.getId());
        user.setUsername(curtent_user.getUsername());
        user.setRole(Const.USERROLE.CUSTOMERUSER);

        return userService.update_information(user);
    }

    /**
     * 获取当前用户的详细信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/get_information.do")
    public ServerResponse get_information(HttpSession session){
        User curtent_user = (User)session.getAttribute(Const.CURRENT_USER);
        if(curtent_user == null){
            //用户未登录或者登录过期，当前端收到status=10跳转到登录页面
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return ServerResponse.createBySussess("成功", curtent_user);
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout.do")
    public ServerResponse logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySussess("退出成功！");
    }


}
