package com.neuedu.service.Impl;
import com.neuedu.dao.*;
import com.neuedu.pojo.*;
import com.neuedu.utils.DateUtil;
import com.neuedu.utils.PropertiesUtils;
import com.neuedu.vo.OrderItemVo;
import com.neuedu.vo.ShippingVo;
import java.math.BigDecimal;

import com.google.common.collect.Lists;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.service.IOrderService;
import com.neuedu.utils.BigDecimalUtil;
import com.neuedu.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public ServerResponse createOrder(Integer userId, Integer shippingId) {
        //step1:参数校验
        if(shippingId == null){
            return ServerResponse.createByError("地址必须选择");
        }
        //查询购物车中勾选的商品
        List<Cart> cartList = this.cartMapper.selectCheckedCartByUserId(userId);

        //List<cart> -> List<OrderItem>
        ServerResponse serverResponse = this.getCartOrderItem(userId, cartList);
        if(!serverResponse.isSucces()){
            return serverResponse;
        }

        //创建并保存订单
        List<OrderItem> orderItemList = (List<OrderItem>)serverResponse.getData();
        BigDecimal orderTotalPrice = this.getOrderTotalPrice(orderItemList);
        Order order = this.assembleOrder(userId, shippingId, orderTotalPrice);
        if(order == null){
            return ServerResponse.createByError("订单生成失败");
        }

        //批量插入List<OrderItem>
        if(orderItemList == null || orderItemList.size() == 0){
            return ServerResponse.createByError("购物车为空");
        }
        for(OrderItem orderItem : orderItemList){
            orderItem.setOrderNo(order.getOrderNo());
        }
        //订单明细批量插入mybatis
        orderItemMapper.batchInsertOrderItem(orderItemList);

        //减去商品库存
        this.reduceProductStock(orderItemList);
        //清空已经购买的商品
        this.cleanCart(cartList);
        //返回OrderVo
        OrderVo orderVo = this.assembleOrderVo(order, orderItemList);

        return ServerResponse.createBySussess(orderVo);
    }

    /**
     * order 转 orderVo
     * @param order
     * @param orderItemList
     * @return
     */
    private OrderVo assembleOrderVo(Order order, List<OrderItem> orderItemList){
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeof(order.getPaymentType()).getDesc());
        orderVo.setPostage(0);
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeof(order.getStatus()).getDesc());
        orderVo.setPaymentTime(DateUtil.dateToString(order.getPaymentTime()));
        orderVo.setSendTime(DateUtil.dateToString(order.getSendTime()));
        orderVo.setEndTime(DateUtil.dateToString(order.getEndTime()));
        orderVo.setCloseTime(DateUtil.dateToString(order.getCloseTime()));
        orderVo.setCreateTime(DateUtil.dateToString(order.getCreateTime()));
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        if(orderItemList != null){
            for(OrderItem orderItem : orderItemList){
                OrderItemVo orderItemVo = this.assembleOrderItemVo(orderItem);
                orderItemVoList.add(orderItemVo);
            }
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        orderVo.setImageHost((String)PropertiesUtils.getProperty("imagehost"));
        orderVo.setShippingId(order.getShippingId());
        Shipping shipping = this.shippingMapper.selectByPrimaryKey(order.getShippingId());
        if(shipping != null){
            orderVo.setRecevierName(shipping.getReceiverName());
            orderVo.setShippingVo(this.assembleShippingVo(shipping));
        }
        return orderVo;
    }

    /**
     * orderItem 转 orderItemVo
     * @param orderItem
     * @return
     */
    private OrderItemVo assembleOrderItemVo(OrderItem orderItem){
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());
        orderItemVo.setCreateTime(DateUtil.dateToString(orderItem.getCreateTime()));
        return orderItemVo;
    }

    /**
     * shipping 转 shippingVo
     * @param shipping
     * @return
     */
    private ShippingVo assembleShippingVo(Shipping shipping){
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverPhone(shipping.getReceiverPhone());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        return shippingVo;
    }

    /**
     * 清空购物车
     * @param cartList
     */
    private void cleanCart(List<Cart> cartList){
        for(Cart cart : cartList){
            this.cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    /**
     * 减少商品库存
     * @param orderItemList
     */
    private void reduceProductStock(List<OrderItem> orderItemList){
        for(OrderItem orderItem : orderItemList){
            Product product = this.productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock() - orderItem.getQuantity());
            this.productMapper.updateByPrimaryKeySelective(product);
        }
    }

    /**
     * 生成订单
     * @param userId
     * @param shippingId
     * @param payment
     * @return
     */
    private Order assembleOrder(Integer userId, Integer shippingId, BigDecimal payment){
        Order order = new Order();
        order.setOrderNo(this.generateOrderNo());
        order.setUserId(userId);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setPaymentType(Const.PaymentTypeEnum.PAY_ONLINE.getStatus());
        order.setPostage(0);
        order.setStatus(Const.OrderStatusEnum.ORDER_UN_PAY.getStatus());
        int result = this.orderMapper.insert(order);
        if(result > 0){
            return order;
        }
        return null;
    }

    /**
     * 生成订单号
     * @return
     */
    private long generateOrderNo(){
        return System.currentTimeMillis() + new Random().nextInt(100);
    }

    /**
     * 计算订单总价
     * @param orderItemList
     * @return
     */
    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList){
        BigDecimal totalPrice = new BigDecimal("0");
        for(OrderItem orderItem : orderItemList){
            totalPrice = BigDecimalUtil.add(totalPrice.doubleValue(), orderItem.getTotalPrice().doubleValue());
        }
        return totalPrice;
    }


    /**
     * 将购物车信息转为订单明细信息
     * @param userId
     * @param cartList
     * @return
     */
    private ServerResponse<List<OrderItem>> getCartOrderItem(Integer userId, List<Cart> cartList){
        List<OrderItem> orderItemList = Lists.newArrayList();
        if(cartList == null || cartList.size() == 0){
            return ServerResponse.createByError("购物车为空");
        }

        for(Cart cart : cartList){
            OrderItem orderItem = new OrderItem();

            Product product = this.productMapper.selectByPrimaryKey(cart.getProductId());
            if(product == null){
                return ServerResponse.createByError("商品不存在");
            }
            Integer productStatus = product.getStatus();
            if(productStatus != Const.productStatusEnum.PRODUCT_ONLINE.getStatus()){
                return ServerResponse.createByError("商品"+ product.getName() +"已经下架");
            }
            Integer productStock = product.getStock();
            if(productStock < cart.getQuantity()){
                return ServerResponse.createByError("商品"+ product.getName() +"库存不足");
            }

            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(), cart.getQuantity().doubleValue()));
            orderItemList.add(orderItem);
        }

        return ServerResponse.createBySussess(orderItemList);
    }
}
