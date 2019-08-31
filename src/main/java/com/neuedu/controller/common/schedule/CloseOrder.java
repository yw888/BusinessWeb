package com.neuedu.controller.common.schedule;

import com.neuedu.service.IOrderService;
import com.neuedu.utils.DateUtil;
import com.neuedu.utils.PropertiesUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时关单
 */
@Component
public class CloseOrder {

    @Autowired
    private IOrderService orderService;

//    @Scheduled(cron = "0/1 * * * * *")
    public void closeOrder(){
        System.out.println("=====执行定时任务调度=======");

//        查询未支付的订单，且超过过期时间


        Integer hour =Integer.parseInt( PropertiesUtils.getProperty("close.order.hour").toString());
        //关闭hour之前的订单
        String closeOrderTime = DateUtil.dateToString(DateUtils.addHours(new Date(), -hour));

        this.orderService.closeOrder(closeOrderTime);

    }
}
