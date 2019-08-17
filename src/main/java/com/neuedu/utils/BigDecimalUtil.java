package com.neuedu.utils;

import java.math.BigDecimal;

/**
 * 价格计算工具类
 */
public class BigDecimalUtil {
    /**
     * 加法
     */
    public static BigDecimal add(Double v1, Double v2){
        BigDecimal num1 = new BigDecimal(v1.toString());
        BigDecimal num2 = new BigDecimal(v2.toString());
        return num1.add(num2);
    }

    /**
     * 减法
     */
    public static BigDecimal sub(Double v1, Double v2){
        BigDecimal num1 = new BigDecimal(v1.toString());
        BigDecimal num2 = new BigDecimal(v2.toString());
        return num1.subtract(num2);
    }

    /**
     * 乘法
     */
    public static BigDecimal mul(Double v1, Double v2){
        BigDecimal num1 = new BigDecimal(v1.toString());
        BigDecimal num2 = new BigDecimal(v2.toString());
        return num1.multiply(num2);
    }

    /**
     * 除法
     */
    public static BigDecimal divi(Double v1, Double v2){
        BigDecimal num1 = new BigDecimal(v1.toString());
        BigDecimal num2 = new BigDecimal(v2.toString());
//        保留两位小数，四舍五入
        return num1.divide(num2, 2, BigDecimal.ROUND_HALF_UP);
    }




}
