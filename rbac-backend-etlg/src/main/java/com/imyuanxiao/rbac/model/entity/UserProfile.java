package com.imyuanxiao.rbac.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName user_profile
 */
@TableName(value ="user_profile")
@Data
@Accessors(chain = true)
public class UserProfile implements Serializable {
    /**
     * profile ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * user ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * player data
     */
    @TableField(value = "save")
    private Object save;

    /**
     * avatar Id
     */
    @TableField(value = "avatar")
    private Integer avatar;

    /**
     * nick name
     */
    @TableField(value = "nick_name")
    private String nickName;

    /**
     * player score
     */
    @TableField(value = "player_score")
    private double playerScore;

    /**
     * achievement points
     */
    @TableField(value = "achievement")
    private Integer achievement;

    /**
     * completed charpters
     */
    @TableField(value = "learning_progress")
    private double learningProgress;

    /**
     *
     */
    @TableField(value = "boss1")
    private Double boss1;

    /**
     *
     */
    @TableField(value = "boss2")
    private Double boss2;

    /**
     *
     */
    @TableField(value = "boss3")
    private Double boss3;

    /**
     *
     */
    @TableField(value = "boss4")
    private Double boss4;

    /**
     *
     */
    @TableField(value = "boss5")
    private Double boss5;

    /**
     *
     */
    @TableField(value = "boss6")
    private Double boss6;

    /**
     *
     */
    @TableField(value = "boss7")
    private Double boss7;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}