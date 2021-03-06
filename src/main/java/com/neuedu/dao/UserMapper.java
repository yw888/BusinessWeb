package com.neuedu.dao;

import com.neuedu.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    List<User> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table neuedu_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(User record);

    /**
     * 检查用户名
     * @param username
     * @return
     */
    int checkUserName(String username);

    /**
     * 校验邮箱
     * @param email
     * @return
     */
    int checkEmail(String email);

    /**
     * 根据用户名和密码查询
     * @param username
     * @param password
     * @return
     */
    User selectLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名查询密保问题
     * @param username
     * @return
     */
    String selectQuestionByUsername(String username);

    /**
     * 校验用户密保答案
     * @param username
     * @param questin
     * @param answer
     * @return
     */
    int check_forget_answer(@Param("username") String username, @Param("question") String questin, @Param("answer") String answer);

    /**
     * 根据用户名修改密码
     * @param username
     * @param password
     * @return
     */
    int updatePassWordByUsernane(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名和旧密码查询
     * @param userId
     * @param password
     * @return
     */
    int selectCountByuserIdAndPassword(@Param("userId") int userId, @Param("password") String password);

    /**
     * 更新
     * @param user
     * @return
     */
    int updateBySelectedActive(User user);

    /**
     * 校验邮箱
     */
    int checkEmailByUserId(@Param("userId") int userId, @Param("emailNew") String email);
}