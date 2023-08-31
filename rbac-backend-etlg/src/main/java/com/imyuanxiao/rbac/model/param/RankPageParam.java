package com.imyuanxiao.rbac.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description get user list by conditions
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Data
public class RankPageParam {

    @NotNull(message = "current is required.")
    private Integer current;

    @NotNull(message = "pageSize is required.")
    private Integer pageSize;

    private Integer type;

/*    private String username;
    private String userPhone;
    private String userEmail;

    private Integer userStatus;*/

}
