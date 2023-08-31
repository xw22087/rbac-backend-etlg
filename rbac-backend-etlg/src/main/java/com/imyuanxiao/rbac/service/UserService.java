package com.imyuanxiao.rbac.service;


import com.imyuanxiao.rbac.model.param.*;
import com.imyuanxiao.rbac.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.rbac.model.vo.LoginResponseVO;
import com.imyuanxiao.rbac.model.vo.TokenResponseVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
* @author Administrator
* @description 针对表【user(user table)】的数据库操作Service
* @date  2023-05-26 17:15:53
*/
public interface UserService extends IService<User> {

//    User getUserByUsername(String username);

    /**
     * @description Register
     * @author imyuanxiao
     * @param param Register form parameters
     **/
    String register(LoginRequestParam param);

    /**
     * @description Log in
     * @author imyuanxiao
     * @param param Login form parameters
     **/
    LoginResponseVO login(LoginRequestParam param, HttpServletRequest request);

    /**
     * @description Add new user
     * @author imyuanxiao
     * @param param user form parameters
     **/
    void createUser(UserParam param);

    /**
     * @description Update user information
     * @author imyuanxiao
     * @param param user form parameters
     **/
    void update(UserParam param);

    TokenResponseVO updateToken();

    //String sendCaptcha(String phone);

    void logout(HttpServletRequest request);

    boolean removeUserByIds(Collection<?> idList);

    void updateUserProfile(UserProfileParam param);

    void updateUserPassword(UserPasswordParam param);

}
