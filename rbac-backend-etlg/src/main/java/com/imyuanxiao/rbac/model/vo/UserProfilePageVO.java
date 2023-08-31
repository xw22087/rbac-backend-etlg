package com.imyuanxiao.rbac.model.vo;

import lombok.Data;

/**
 * @description  User pagination object.
 * @author  imyuanxiao
 **/
@Data
public class UserProfilePageVO {

    private Long userId;

    private String nickName;

    private Object data;

   /* private int userStatus;
    private String userPhone;
    private String userEmail;*/

   /* private Set<Long> roleIds;
    private Set<Long> orgIds;*/
}
