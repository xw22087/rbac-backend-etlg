package com.imyuanxiao.rbac.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class UserProfileVO {

    private Integer avatar;

    private String nickName;

    private Integer playerScore;

    private Integer achievement;

    private Integer learningProgress;

    private Double boss1;
    private Double boss2;
    private Double boss3;
    private Double boss4;
    private Double boss5;
    private Double boss6;
    private Double boss7;
}