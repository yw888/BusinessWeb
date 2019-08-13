package com.neuedu.common;

public class Const {
    public static final String CURRENT_USER="current_user";
    public static final String USERNAME = "username";
    public static final String EMAIL ="email";

    public interface USERROLE{
        public int ADMINUSER = 0;//管理员
        public int CUSTOMERUSER = 1;//普通用户

    }

    public enum productStatusEnum{
        PRODUCT_ONLINE(1, "在售"),
        PRODCUT_OFFLINE(2, "下架"),
        PRODUCT_DELETE(3, "删除")
        ;
        private int status;
        private String desc;

        private productStatusEnum(int status, String desc) {
            this.status = status;
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public String getDesc() {
            return desc;
        }
    }

}
