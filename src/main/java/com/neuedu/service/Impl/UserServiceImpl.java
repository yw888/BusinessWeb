package com.neuedu.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neuedu.common.Const;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.UserMapper;
import com.neuedu.pojo.User;
import com.neuedu.service.IUserService;
import com.neuedu.utils.GuavaCacheUtil;
import com.neuedu.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public ServerResponse login(String username, String password) {

        //step1:校验用户名
        int result = userMapper.checkUserName(username);
        //step2:登录操作
        if(result > 0){
            //MD5(password)
            password = MD5Utils.GetMD5Code(password);
            //用户存在
            User user = userMapper.selectLogin(username, password);
            if(user == null){
                //密码错误
                return ServerResponse.createByError("密码错误");
            }else{
                user.setPassword("");
                return ServerResponse.createBySussess("success",user);
            }
        }else{
            //用户名不存在
            return ServerResponse.createByError("用户名不存在");

        }
    }

    @Override
    public ServerResponse register(User user) {
        //校验用户名
//        int result = userMapper.checkUserName(user.getUsername());
//        if(result > 0){
//            return ServerResponse.createByError("用户名已存在！");
//        }
        ServerResponse  serverResponse = checkValid(user.getUsername(), Const.USERNAME);
        if(!serverResponse.isSucces()){
            return serverResponse;
        }
        //校验邮箱
//        int email_result = userMapper.checkEmail(user.getEmail());
//        if(email_result > 0){
//            return ServerResponse.createByError("已存在邮箱！");
//        }
        ServerResponse  serverResponse1 = checkValid(user.getEmail(), Const.EMAIL);
        if(!serverResponse1.isSucces()){
            return serverResponse1;
        }
        //加密算法：hash算法（不对称加密）md5，RSA
        //MD5加密
        user.setPassword(MD5Utils.GetMD5Code(user.getPassword()));
        user.setRole(Const.USERROLE.CUSTOMERUSER);//0：管理员 1 ：普通用户
        //将用户插入到数据库
        int inset_result = userMapper.insert(user);
        if(inset_result > 0){
            //注册成功
            return ServerResponse.createBySussess("注册成功！");
        }
        return ServerResponse.createByError("注册失败！");
    }

    @Override
    public ServerResponse checkValid(String str, String type) {
        if(type == null || type.equals("")){
            return ServerResponse.createByError("参数必须传递！");
        }
        if(str == null || str.equals("")){
            return ServerResponse.createByError("参数必须传递");
        }
        if(type.equals(Const.USERNAME)){
            //校验用户名
            int username_result = userMapper.checkUserName(str);
            if(username_result > 0){
                //存在
                return ServerResponse.createByError("用户名已存在！");
            }
        }
        if(type.equals(Const.EMAIL)){
            //校验邮箱
            int email_result = userMapper.checkEmail(str);
            if(email_result > 0){
                return ServerResponse.createByError("邮箱已存在！");
            }
        }
        return ServerResponse.createBySussess("校验成功！");
    }

    @Override
    public ServerResponse forget_get_question(String username) {
        if(username == null || username.equals("")){
            return ServerResponse.createByError("用户名不能为空！");
        }
        //校验用户名是否存在
        int user_result = userMapper.checkUserName(username);
        if( user_result > 0){
            //用户名存在
            //根据用户名查询密保问题
            String question = userMapper.selectQuestionByUsername(username);
            if(question == null || question.equals("")){
                return ServerResponse.createByError("未找到密保问题！");
            }
            return ServerResponse.createBySussess("成功",question);
        }
        return ServerResponse.createByError("用户不存在！");
    }

    @Override
    public ServerResponse forget_answer(String username, String question, String answer) {
        if(username == null || username.equals("")){
            return ServerResponse.createByError("用户名必须传递！");
        }
        if(question == null || question.equals("")){
            return ServerResponse.createByError("问题必须传递！");
        }
        if(answer == null || answer.equals("")){
            return ServerResponse.createByError("答案必须传递！");
        }

        int result = userMapper.check_forget_answer(username, question, answer);
        if(result > 0){
            //答案正确
            //生成忘记密码的token
            String token = UUID.randomUUID().toString();
            //保存到服务端一份-----guava cache
            GuavaCacheUtil.put(username,token);
            return ServerResponse.createBySussess("成功！", token);
        }

        return null;
    }

    @Override
    public ServerResponse forget_reset_password(String username, String passwordNew, String forgetToken) {
        if(username == null || username.equals("")){
            return ServerResponse.createByError("用户名必须传递！");
        }
        if(forgetToken == null || forgetToken.equals("")){
            return ServerResponse.createByError("token必须传递！");
        }
        if(passwordNew == null || passwordNew.equals("")){
            return ServerResponse.createByError("密码必须传递！");
        }
        //校验用户名是否存在
        ServerResponse serverResponse = checkValid(username, Const.USERNAME);
        if(serverResponse.isSucces()){//不存在
            return ServerResponse.createByError("用户名不存在!");
        }
        //获取用户存在服务器的token
        String token = GuavaCacheUtil.get(username);
        if(token == null){
            return ServerResponse.createByError("token不存在或者已经过期！");
        }
        //判断用户传递token与服务器的token是否一致
        if(forgetToken.equals(token)){
            //修改密码
            passwordNew = MD5Utils.GetMD5Code(passwordNew);
            int result = userMapper.updatePassWordByUsernane(username, passwordNew);
            if (result > 0) {
                return ServerResponse.createBySussess("修改成功！");
            }else{
                return ServerResponse.createByError("修改失败！");
            }
        }else{
            return ServerResponse.createByError("token错误，请重新获取！");
        }
    }

    @Override
    public ServerResponse reset_password(String passwordOld, String passwordNew, User user) {
        //step1非空校验
        if(passwordOld == null || passwordOld.equals("")){
            return ServerResponse.createByError("旧密码不能为空！");
        }
        if(passwordNew == null || passwordNew.equals("")){
            return ServerResponse.createByError("新密码不能为空！");
        }

        //step2:根据userId和passwordOld校验，防止横向越权
        int result = userMapper.selectCountByuserIdAndPassword(user.getId(),MD5Utils.GetMD5Code(passwordOld));
        if(result > 0){
            //用户的旧密码正确
            user.setPassword(MD5Utils.GetMD5Code(passwordNew));
            int update_result = userMapper.updateBySelectedActive(user);
            if(update_result > 0){
                //更新成功
                return ServerResponse.createBySussess("密码更新成功！");
            }else{
                return ServerResponse.createByError("密码更新失败！");
            }
        }else{
            return ServerResponse.createByError("");
        }
    }

    @Override
    public ServerResponse update_information(User user) {
        //校验邮箱是否存在
        int result = userMapper.checkEmailByUserId(user.getId(), user.getEmail());
        if(result > 0){
            //邮箱已存在
            return ServerResponse.createByError("邮箱已存在！");
        }
        //更新用户信息
        int update_result = userMapper.updateBySelectedActive(user);
        if(update_result > 0){
            return ServerResponse.createBySussess("更新成功");
        }else{
            return ServerResponse.createByError("更新失败！");
        }
    }

    @Override
    public ServerResponse selectUserByPageNo(int pageNo, int pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        List<User> userList = userMapper.selectAll();
        //分页模型
        PageInfo pageInfo = new PageInfo(userList);

        return ServerResponse.createBySussess("成功", pageInfo);
    }

    @Override
    public ServerResponse checkUserAdmin(User user) {
        if(user.getRole().intValue() == Const.USERROLE.ADMINUSER ){
            return ServerResponse.createBySussess();
        }
        return ServerResponse.createByError();
    }
}
