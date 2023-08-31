package com.imyuanxiao.rbac.controller.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imyuanxiao.rbac.annotation.Auth;
import com.imyuanxiao.rbac.model.entity.UserProfile;
import com.imyuanxiao.rbac.model.param.RankPageParam;
import com.imyuanxiao.rbac.model.param.UserPasswordParam;
import com.imyuanxiao.rbac.model.param.UserProfileParam;
import com.imyuanxiao.rbac.model.vo.UserProfilePageVO;
import com.imyuanxiao.rbac.model.vo.UserProfileVO;
import com.imyuanxiao.rbac.service.UserProfileService;
import com.imyuanxiao.rbac.service.UserService;
import com.imyuanxiao.rbac.util.SecurityContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static com.imyuanxiao.rbac.util.CommonConst.ACTION_SUCCESSFUL;

/**
 * @description  User Management Interface
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 **/
@Slf4j
@RestController
@RequestMapping("/profile")
@Auth(id = 2000, name = "Profile Interface for User")
@Api(tags = "Profile Interface for User")
public class ProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserService userService;

    @PutMapping("/update")
    @ApiOperation(value = "Update user profile")
    public String updateUserProfile(@RequestBody UserProfileParam param) {
        userService.updateUserProfile(param);
        return ACTION_SUCCESSFUL;
    }

    @PutMapping("/password")
    @ApiOperation(value = "Update user password")
    public String updateUserPassword(@RequestBody UserPasswordParam param) {
        userService.updateUserPassword(param);
        return ACTION_SUCCESSFUL;
    }

    @PostMapping("/rank")
    public IPage<UserProfilePageVO> getRankList(@RequestBody RankPageParam param) {
        return userProfileService.getRankList(param);
    }

    @PostMapping("/saveUpload")
    @ApiOperation(value = "Save player data")
    public String savePlayerData(@RequestBody HashMap<String, String> saveData) {
        double playerScore = Double.parseDouble(saveData.get("PlayerScore"));
        int achievementScore = Integer.parseInt(saveData.get("AchievementScore"));
        double learningProgress = Double.parseDouble(saveData.get("LearningProgress"));
        JSONArray bossDefeatTime = JSONUtil.parseArray(saveData.get("BossDefeatTime"));
        double[] bossTime = bossDefeatTime.stream().mapToDouble(item -> Double.parseDouble(item.toString())).toArray();
        UserProfile userProfile = new UserProfile();
        userProfile.setSave(JSONUtil.toJsonStr(saveData))
                .setAchievement(achievementScore)
                .setLearningProgress(learningProgress)
                .setPlayerScore(playerScore)
                .setBoss1(bossTime[0])
                .setBoss2(bossTime[1])
                .setBoss3(bossTime[2])
                .setBoss4(bossTime[3])
                .setBoss5(bossTime[4])
                .setBoss6(bossTime[5])
                .setBoss7(bossTime[6])
                .setUserId(SecurityContextUtil.getCurrentUserId());
        userProfileService.updateByUserId(userProfile);
        return ACTION_SUCCESSFUL;
    }

    @GetMapping("/saveDownload")
    @ApiOperation(value = "Save player data")
    public String savePlayerData() {
        String saveJson = (String)userProfileService.getByUserId(SecurityContextUtil.getCurrentUserId()).getSave();
        return saveJson;
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "Get user profile info based on user ID")
    public UserProfileVO getUserById(@ApiParam(value = "User ID", required = true)
                            @PathVariable("id") Long id) {
        UserProfile userProfile = userProfileService.getByUserId(id);
        return BeanUtil.copyProperties(userProfile, UserProfileVO.class);
    }

}
