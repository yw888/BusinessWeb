package com.neuedu.service.Impl;
import java.util.Date;
import java.math.BigDecimal;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.CartMapper;
import com.neuedu.dao.ProductMapper;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICartService;
import com.neuedu.utils.BigDecimalUtil;
import com.neuedu.utils.PropertiesUtils;
import com.neuedu.vo.CartProductVo;
import com.neuedu.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();

        List<Cart> cartList = this.cartMapper.findCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();
        BigDecimal totalPrice = new BigDecimal("0");
        if(cartList != null){
            for(Cart cart : cartList){
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cart.getId());
                cartProductVo.setUserId(userId);
                cartProductVo.setProductId(cart.getProductId());
                //查询商品
                Product product = this.productMapper.selectByPrimaryKey(cart.getProductId());
                if(product != null){
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductMainImage("");
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductStock(product.getStock());

                    int buyLimitCount = 0;
                    if(product.getStock() > cart.getQuantity()){
                        //库存充足
                        buyLimitCount = cart.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCESS);
                    }else{
                        //库存不足
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        Cart cart1 = new Cart();
                        cart1.setId(cart.getId());
                        cart1.setQuantity(product.getStock());
                        cartMapper.updateByPrimaryKeyBySelectActive(cart1);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    BigDecimal cartProductTotalPrice = BigDecimalUtil.mul(product.getPrice().doubleValue(), cart.getQuantity().doubleValue());
                    cartProductVo.setProductTotalPrice(cartProductTotalPrice);
                    cartProductVo.setProductChecked(cart.getChecked());
                    if(cart.getChecked() == Const.Cart.CHECKED){
                        totalPrice = BigDecimalUtil.add(totalPrice.doubleValue(), cartProductTotalPrice.doubleValue());
                    }
                }
                cartProductVoList.add(cartProductVo);
            }
        }

        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setCartTotalPrice(totalPrice);
        cartVo.setAllChecked(cartMapper.isAllChecked(userId) == 0);
        cartVo.setImageHost(PropertiesUtils.getProperty("imagehost").toString());

        return cartVo;
    }

    @Override
    public ServerResponse add(Integer userId, Integer productId, Integer count) {
        if(userId == null || count == null){
            return ServerResponse.createByError("参数错误！");
        }
        Cart cart = this.cartMapper.findCartByProductIdAndUserId(productId, userId);
        if(cart == null){
            //购物车没有改商品
            Cart cartItem = new Cart();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(cartItem);
        }else{
            //存在，更新商品数量
            cart.setQuantity(cart.getQuantity() + count);
            cartMapper.updateByPrimaryKeyBySelectActive(cart);
        }

        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySussess("成功", cartVo);
    }

    @Override
    public ServerResponse list(Integer userId) {
        CartVo cartVo = this.getCartVoLimit(userId);
        return ServerResponse.createBySussess("成功", cartVo);
    }

    @Override
    public ServerResponse update(Integer userId, Integer productId, Integer count) {
        if(userId == null || count == null){
            return ServerResponse.createByError("参数错误！");
        }
        Cart cart = this.cartMapper.findCartByProductIdAndUserId(productId, userId);
        if(cart != null){
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeyBySelectActive(cart);
        CartVo cartVo = this.getCartVoLimit(userId);

        return ServerResponse.createBySussess("成功", cartVo);
    }

    @Override
    public ServerResponse delete(Integer userId, String productIds) {
        if(productIds == "" || productIds == null){
            return ServerResponse.createByError("参数错误！");
        }
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        if(productIdList == null || productIdList.size() <= 0){
            return ServerResponse.createByError("参数错误！");
        }
        this.cartMapper.deleteByUserIdAndProductIds(userId, productIdList);
        CartVo cartVo = this.getCartVoLimit(userId);

        return ServerResponse.createBySussess("成功", cartVo);
    }

    @Override
    public ServerResponse selectOrUnselect(Integer userId, Integer productId, Integer checked) {

        this.cartMapper.checkedOrUnCheckedProduct(userId, productId, checked);
        CartVo cartVo = this.getCartVoLimit(userId);

        return ServerResponse.createBySussess("成功", cartVo);
    }

    @Override
    public ServerResponse<Integer> get_cart_product_count(Integer userId) {
        int count = this.cartMapper.selectCartProductCount(userId);
        return ServerResponse.createBySussess(count);
    }
}
