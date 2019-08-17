package com.neuedu.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ShippingMapper;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse add(Integer userId, Shipping shipping) {
        if(shipping == null){
            return ServerResponse.createByError("参数错误");
        }
        shipping.setUserId(userId);
        int result = this.shippingMapper.insert(shipping);
        if(result > 0){
            Map<String, Integer> map = Maps.newHashMap();
            map.put("shippingId", shipping.getId());
            return ServerResponse.createBySussess(map);
        }
       return ServerResponse.createByError("添加失败");
    }

    @Override
    public ServerResponse delete(Integer userId, Integer shippingId) {

        if(shippingId == null){
            return ServerResponse.createByError("shippingId必须传递");
        }
        int result = this.shippingMapper.deleteByPrimaryKeyAndUserId(userId, shippingId);
        if(result > 0){
            return ServerResponse.createBySussess("删除成功");
        }
        return ServerResponse.createByError("删除失败");
    }

    @Override
    public ServerResponse update(Integer userId, Shipping shipping) {
        if(shipping == null){
            return ServerResponse.createByError("参数错误");
        }
        //防止横向越权
        shipping.setUserId(userId);
        int result = this.shippingMapper.updateByPrimaryKey(shipping);
        if(result > 0){
            return ServerResponse.createBySussess("地址更新成功");
        }
        return ServerResponse.createByError("地址更新失败");
    }

    @Override
    public ServerResponse select(Integer userId, Integer shippingId) {
        if(shippingId == null){
            return ServerResponse.createByError("shippingId必须传递");
        }
        Shipping shipping = this.shippingMapper.selectByPrimaryKeyAndUserId(userId, shippingId);
        if(shipping != null){
            return ServerResponse.createBySussess("成功", shipping);
        }
        return ServerResponse.createByError("没有该地址");
    }

    @Override
    public ServerResponse list(Integer userId, Integer pageNo, Integer pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        List<Shipping> shippingList = this.shippingMapper.selectAllByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);

        return ServerResponse.createBySussess("成功", pageInfo);
    }
}
