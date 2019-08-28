package com.neuedu.controller.backend;

import com.neuedu.common.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;
import com.neuedu.service.IOrderService;
import com.neuedu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/order")
public class OrderManageController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IOrderService orderService;

    /**
     * 订单列表
     * @param session
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(required = false, defaultValue = "1") Integer pageNo,
                               @RequestParam(required = false, defaultValue = "10")Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        return this.orderService.list(user.getId(), pageNo, pageSize);
    }

    /**
     * 根据订单号查询
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("/search.do")
    public ServerResponse search(HttpSession session, Long orderNo){

        return this.orderService.search(orderNo);
    }

    /**
     * 订单详情
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("/detail.do")
    public ServerResponse detail(HttpSession session, Long orderNo){

        return this.orderService.search(orderNo);
    }

    @RequestMapping("/send.do")
    public ServerResponse send(HttpSession session, Long orderNo){
        return this.orderService.send(orderNo);
    }


}
