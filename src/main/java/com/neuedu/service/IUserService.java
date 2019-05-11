package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.User;

public interface IUserService {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public ServerResponse login(String username,String password);

    /**
     * 注册
     * @param user
     * @return
     */
    public ServerResponse register(User user);

    /**
     * 检查是否有效
     * @param str
     * @param type
     * @return
     */
    public ServerResponse checkValid(String str, String type);

    /**
     * 获取密保问题
     * @param username
     * @return
     */
    public ServerResponse forget_get_question(String username);

    /**
     * 忘记密码-提交问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    public ServerResponse forget_answer(String username, String question, String answer);

    /**
     * 忘记密码-修改密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken);

    /**
     * 登录状态下的重置密码
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    public ServerResponse reset_password(String passwordOld, String passwordNew, User user);

    /**
     * 登录状态下更新用户信息
     * @param user
     * @return
     */
    public ServerResponse update_information(User user);

    /**
     * 分页查询
     * @param pageNo 页码（第几页）
     * @param pageSize 查询数据量
     * @return
     */
    public ServerResponse selectUserByPageNo(int pageNo, int pageSize);

    /**
     * 检查用户是否是管理员
     * @param user
     * @return
     */
    public ServerResponse checkUserAdmin(User user);


}
