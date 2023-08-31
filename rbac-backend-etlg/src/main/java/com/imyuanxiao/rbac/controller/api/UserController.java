package com.imyuanxiao.rbac.controller.api;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;

import com.imyuanxiao.rbac.annotation.Auth;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.param.*;
import com.imyuanxiao.rbac.model.entity.User;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.util.ValidationGroups;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static com.imyuanxiao.rbac.util.CommonConst.*;

/**
 * @description  User Management Interface
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
@RestController
@RequestMapping("/user")
@Auth(id = 1000, name = "User Management Interface for Admin")
@Api(tags = "User Management Interface for Admin")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    @Auth(id = 1, name = "Add User")
    @ApiOperation(value = "Add user")
    public String createUser(@RequestBody @Validated(ValidationGroups.AddUser.class) UserParam param) {
        userService.createUser(param);
        return ACTION_SUCCESSFUL;
    }

    @DeleteMapping("/delete")
    @Auth(id = 2, name = "Delete user")
    @ApiOperation(value = "Delete user")
    public String deleteUser(@RequestBody Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            throw new ApiException(ResultCode.PARAMS_ERROR);
        }
        userService.removeUserByIds(Arrays.asList(ids));
        return ACTION_SUCCESSFUL;
    }

    @PutMapping("/update")
    @Auth(id = 3, name = "Update user")
    @ApiOperation(value = "Update user")
    public String updateUser(@RequestBody @Validated(ValidationGroups.UpdateUser.class) UserParam param) {
        userService.update(param);
        return ACTION_SUCCESSFUL;
    }

    @GetMapping("/get/{id}")
    @Auth(id = 4, name = "Get user info based on user ID")
    @ApiOperation(value = "Get user info based on user ID")
    public User getUserById(@ApiParam(value = "User ID", required = true)
                            @PathVariable("id") Long id) {
        return userService.getById(id);
    }

}
