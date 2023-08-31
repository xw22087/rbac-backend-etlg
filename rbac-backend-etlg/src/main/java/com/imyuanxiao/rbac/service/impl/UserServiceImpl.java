package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.mapper.UserMapper;
import com.imyuanxiao.rbac.model.entity.UserProfile;
import com.imyuanxiao.rbac.model.param.*;
import com.imyuanxiao.rbac.model.vo.*;
import com.imyuanxiao.rbac.security.JwtManager;
import com.imyuanxiao.rbac.service.*;
import com.imyuanxiao.rbac.util.RedisUtil;
import com.imyuanxiao.rbac.util.SecurityContextUtil;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
* @author Administrator
* @description 针对表【user(user table)】的数据库操作Service实现
* @date  2023-05-26 17:15:53
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserProfileService userProfileService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String register(LoginRequestParam param) {

        if (StrUtil.isBlank(param.getPassword())) {
            throw new ApiException(ResultCode.PARAMS_ERROR, "Incorrect password！");
        }

        User user = new User()
                .setUsername(param.getUsername())
                .setPassword(passwordEncoder.encode(param.getPassword()));
        try {
            this.save(user);
            // Add default user role - 2L user
            roleService.insertRolesByUserId(user.getId(), List.of(2L));
            return "Register successfully";
        } catch (Exception e) {
            throw new ApiException(ResultCode.FAILED, "Account is already in use.");
        }
    }

    @Override
    public LoginResponseVO login(LoginRequestParam param, HttpServletRequest request) {

        User userResult = null;

        String username = param.getUsername();
        userResult = this.lambdaQuery()
                .eq(StrUtil.isNotBlank(username), User::getUsername, username)
                .one();
        if (userResult == null || !passwordEncoder.matches(param.getPassword(), userResult.getPassword())) {
            throw new ApiException(ResultCode.VALIDATE_FAILED, "Username or password is incorrect！");
        }

        // Put user basic info, profile, token, permissions in UserVO object
        UserVO userVO = getUserVO(userResult);

        // save UserMap to redis
        // Manually handle or use util to convert id 'long' to 'string'.
        Map<String, Object> userMap = BeanUtil.beanToMap(userVO, new HashMap<>(),
                CopyOptions.create().
                        setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue != null ? fieldValue.toString() : null));
        // Generate token
        String token = JwtManager.generate(userResult.getUsername(), userResult.getId());
        // Add token to userMap
        userMap.put("token", token);
        // Save user info and token in redis
        redisUtil.saveUserMap(userMap);

        // return loginResponse
        return new LoginResponseVO().setUserVO(userVO).setToken(token);
    }

    private UserVO getUserVO(User user) {
        UserVO userVO = new UserVO();
        // Copy basic info
        BeanUtil.copyProperties(user, userVO);
        // Copy user profile
        UserProfile userProfile = userProfileService.getByUserId(user.getId());
        // Initialize user profile if new user
        if (userProfile == null) {
            userProfile = new UserProfile()
                    .setNickName("player" + user.getId())
                    .setAvatar(1);
            userProfile.setUserId(user.getId());
            userProfileService.save(userProfile);
        }
        // copy userProfile data to userVO data，ignore id
        BeanUtil.copyProperties(userProfile, userVO, "id", "userID");

        // Set roleIds and permissionIds
        userVO.setRoleIds(roleService.getIdsByUserId(user.getId()))
                .setPermissionIds(permissionService.getIdsByUserId(user.getId()));
        return userVO;
    }

    @Override
    public void logout(HttpServletRequest request) {
        redisUtil.removeUserMap();
    }


    @Override
    public TokenResponseVO updateToken() {
        String newToken = redisUtil.refreshToken();
        return new TokenResponseVO().setToken(newToken);
    }

    @Override
    public void createUser(UserParam param) {
        checkUsername(param.getUsername());
        User user = new User();
        user.setUsername(param.getUsername()).setPassword(passwordEncoder.encode(param.getUsername()));
        save(user);
        if (CollectionUtil.isEmpty(param.getRoleIds())) {
            return;
        }
        // Add info in table [user-role]
        roleService.insertRolesByUserId(user.getId(), param.getRoleIds());
    }

    private void checkUsername(String username) {
        if (lambdaQuery().eq(User::getUsername, username).one() != null) {
            throw new ApiException(ResultCode.FAILED, "Account already exists.");
        }
    }

    public boolean removeUserByIds(Collection<?> idList) {
        if (CollectionUtil.isEmpty(idList)) {
            return false;
        }

        for (Object userId : idList) {
            // Delete info from table [user-role]
            roleService.removeByUserId((Long) userId);

            // Delete user from redis
            String username = lambdaQuery().eq(User::getId, userId).one().getUsername();
            redisUtil.removeUserMapByUsername(username);

        }
        // Delete user
        baseMapper.deleteBatchIds(idList);
        return true;
    }

    @Override
    public void updateUserProfile(UserProfileParam param) {

        Long currentUserId = SecurityContextUtil.getCurrentUserId();

        // update phone and email
//        lambdaUpdate()
//                .set(StrUtil.isNotBlank(param.getUserPhone()), User::getUserPhone, param.getUserPhone())
//                .set(StrUtil.isNotBlank(param.getUserEmail()), User::getUserEmail, param.getUserEmail())
//                .eq(User::getId, currentUserId).update();

        // update profile
        UserProfile userProfile = BeanUtil.copyProperties(param, UserProfile.class);
        userProfile.setUserId(currentUserId);
        userProfileService.updateByUserId(userProfile);

        // update user data in redis
        User userResult = this.lambdaQuery()
                .eq(User::getId, currentUserId)
                .one();

        // Put user basic info, profile, token, permissions in UserVO object
        UserVO userVO = getUserVO(userResult);

        // save UserMap to redis
        // Manually handle or use util to convert id 'long' to 'string'.
        Map<String, Object> userMap = BeanUtil.beanToMap(userVO, new HashMap<>(),
                CopyOptions.create().
                        setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue != null ? fieldValue.toString() : null));
        // Save user info and token in redis
        redisUtil.saveUserMap(userMap);

    }

    @Override
    public void updateUserPassword(UserPasswordParam param) {

        Long currentUserId = SecurityContextUtil.getCurrentUserId();

        User userResult = lambdaQuery()
                .eq(User::getId, currentUserId).one();

        if (userResult == null || !passwordEncoder.matches(param.getOldPassword(), userResult.getPassword())) {
            throw new ApiException(ResultCode.VALIDATE_FAILED, "Wrong old password！");
        }

        this.lambdaUpdate()
                .set(User::getPassword, passwordEncoder.encode(param.getNewPassword()))
                .eq(User::getId, currentUserId).update();

    }



    @Override
    public void update(UserParam param) {

        // 检查用户名是否存在（排除当前ID）
        if (lambdaQuery().ne(User::getId, param.getId()).eq(User::getUsername, param.getUsername()).one() != null) {
            throw new ApiException(ResultCode.FAILED, "Account already exists.");
        }

        // 提取User信息
        User updateUser = BeanUtil.copyProperties(param, User.class);

        // update user data
        lambdaUpdate().eq(User::getId, updateUser.getId()).update(updateUser);

        // update roles
        updateRoles(param);

    }

    private void updateRoles(UserParam param) {
        // Delete the original user role
        roleService.removeByUserId(param.getId());
        // If roleIds is empty, delete all roles for this user
        if (CollectionUtil.isEmpty(param.getRoleIds())) {
            return;
        }
        // If roleIds not empty, add new roles for this user
        roleService.insertRolesByUserId(param.getId(), param.getRoleIds());
    }

}


