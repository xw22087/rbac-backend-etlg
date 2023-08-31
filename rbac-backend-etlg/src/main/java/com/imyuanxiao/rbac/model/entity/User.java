package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * user table
 * @TableName user
 */
@TableName(value ="user")
@Data
@Accessors(chain = true)
public class User implements Serializable {
    /**
     * user ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long id;

    /**
     * username
     */
    @TableField(value = "username")
    private String username;

    /**
     * user password, encoded
     */
    @TableField(value = "user_password")
    private String password;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}