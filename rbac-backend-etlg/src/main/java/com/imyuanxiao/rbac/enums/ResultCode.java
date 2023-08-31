package com.imyuanxiao.rbac.enums;

import lombok.Getter;

/**
 * @description result code
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Getter
public enum ResultCode {
    // Operation Success
    SUCCESS(0, "Operation successful"),

    // Parameter validation related
    PARAMS_ERROR(40000, "Request parameter error"),
    VALIDATE_FAILED(1006, "Parameter validation failed"),

    // Token related
    UNAUTHORIZED(1001, "Not logged in"),
    INVALID_TOKEN(1002, "Invalid token"),
    TOKEN_EXPIRED(1003, "Token has expired"),

    // Permission related
    FORBIDDEN(1004, "No relevant permissions"),
    UNAUTHORIZED_OPERATION(1005, "Unauthorized operation"),

    // Resource not found
    RESOURCE_NOT_FOUND(1007, "Resource does not exist"),
    USER_NOT_FOUND(1008, "User does not exist"),
    ROLE_NOT_FOUND(1009, "Role does not exist"),
    PERMISSION_NOT_FOUND(1010, "Permission does not exist"),

    // Account related
    ACCOUNT_STATE_DISABLED(1011, "Account is disabled"),
    ACCOUNT_STATE_DELETED(1012, "Account has been deleted"),

    CODE_EXISTS(1013, "Verification code has been sent, please try again later"),
    REGISTER_SUCCESS(1014, "Register successful"),
    ACCOUNT_TAKEOVER(1015, "Account takeover by another user"),

    // Other errors
    FAILED(2001, "Operation failed"),
    DATABASE_ERROR(2002, "Database operation exception"),

    ERROR(5000, "Unknown error"),

    METHOD_NOT_ALLOWED(405, "Method not allowed!"),
    BAD_REQUEST(400, "Bad Request");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
