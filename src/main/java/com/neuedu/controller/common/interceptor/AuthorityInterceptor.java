package com.neuedu.controller.common.interceptor;

import com.google.gson.Gson;
import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * 权限认证拦截器
 */
public class AuthorityInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;
    /**
     * 请求到达controller之前
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("=======preHandle=========");
        HttpSession session = httpServletRequest.getSession();
        User cuser = (User)session.getAttribute(Const.CURRENT_USER);

        //用户未登录或没有管理员权限
        if(cuser == null || !userService.checkUserAdmin(cuser).isSucces()){
            //重构HttpServletResponse
            httpServletResponse.reset();
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            PrintWriter printWriter = httpServletResponse.getWriter();
            Gson gson = new Gson();
            if(cuser == null){
                ServerResponse serverResponse = ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
                String json = gson.toJson(serverResponse);
                printWriter.write(json);
            }else{
                ServerResponse serverResponse = ServerResponse.createByError("用户没有权限");
                String json = gson.toJson(serverResponse);
                printWriter.write(json);
            }
            printWriter.flush();
            printWriter.close();
            return false;
        }

        return true;
    }

    /**
     * controller响应之后调用该方法
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("=======postHandle=========");
    }

    /**
     * 请求完成，当相应到达客户之后
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("=======afterCompletion=========");
    }
}
