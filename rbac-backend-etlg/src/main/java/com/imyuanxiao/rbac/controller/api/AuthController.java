package com.imyuanxiao.rbac.controller.api;

import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.model.param.LoginRequestParam;
import com.imyuanxiao.rbac.model.vo.LoginResponseVO;
import com.imyuanxiao.rbac.model.vo.TokenResponseVO;
import com.imyuanxiao.rbac.model.vo.UserVO;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.util.SecurityContextUtil;
import com.imyuanxiao.rbac.util.ValidationGroups;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @description  登录注册接口
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
@Api(tags = "Auth Management Interface")
public class AuthController {

    @Autowired
    private UserService userService;

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
    * @description: show captcha in frontend (actually should be void)
    * @param param phone
    * @author imyuanxiao
    */
//    @PostMapping("/captcha")
//    @ApiOperation(value = "Get Captcha")
//    public String sendCaptcha(@RequestBody CaptchaParam param){
//        String phone = param.getPhone();
//        if(!PhoneUtil.isPhone(phone)){
//            throw new ApiException(ResultCode.PARAMS_ERROR,"手机号格式错误！");
//        }
//        return userService.sendCaptcha(phone);
//    }

    /**
    * @description: register
    * @param param register-related parameters
    * @author imyuanxiao
    */
    @PostMapping("/register")
    @ApiOperation(value = "Register")
    public String register(@RequestBody @Validated LoginRequestParam param){
        dataValidation(param);
        return userService.register(param);
    }

    private void dataValidation(LoginRequestParam request) {
        Set<ConstraintViolation<LoginRequestParam>> validate = validator.validate(request, ValidationGroups.Account.class);
        if(!validate.isEmpty()){
            String errorMessage = validate.iterator().next().getMessage();
            throw new ApiException(ResultCode.PARAMS_ERROR, errorMessage);
        }
    }

    /**
    * @description: login
    * @param param login-related parameters
    * @author imyuanxiao
    */
    @PostMapping("/login")
    @ApiOperation(value = "Login")
    public LoginResponseVO login(@RequestBody @Validated LoginRequestParam param, HttpServletRequest request){
        dataValidation(param);
        return userService.login(param, request);
    }

    @GetMapping("/currentUser")
    @ApiOperation(value = "Current User Info")
    public UserVO currentUser(){
        return SecurityContextUtil.getCurrentUser();
    }

    @GetMapping("/updateToken")
    @ApiOperation(value = "Update token in redis")
    public TokenResponseVO updateToken(){
        return userService.updateToken();
    }

    @GetMapping("/logout")
    @ApiOperation(value = "Logout")
    public void logout(HttpServletRequest request){ userService.logout(request);}

}
