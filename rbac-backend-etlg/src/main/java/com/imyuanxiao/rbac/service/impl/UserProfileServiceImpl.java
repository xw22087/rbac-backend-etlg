package com.imyuanxiao.rbac.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imyuanxiao.rbac.enums.ResultCode;
import com.imyuanxiao.rbac.exception.ApiException;
import com.imyuanxiao.rbac.mapper.UserProfileMapper;
import com.imyuanxiao.rbac.model.entity.UserProfile;
import com.imyuanxiao.rbac.model.param.RankPageParam;
import com.imyuanxiao.rbac.model.vo.UserProfilePageVO;
import com.imyuanxiao.rbac.service.UserProfileService;
import org.springframework.stereotype.Service;

import static com.imyuanxiao.rbac.util.CommonConst.*;
import static com.imyuanxiao.rbac.util.CommonConst.RANK_BOSS_7;

/**
* @author Administrator
* @description 针对表【user_profile】的数据库操作Service实现
* @createDate 2023-08-03 16:52:43
*/
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile>
    implements UserProfileService{

    @Override
    public UserProfile getByUserId(Long userId) {
        return this.lambdaQuery()
                .eq(UserProfile::getUserId, userId).one();
    }

    public boolean updateByUserId(UserProfile userProfile) {

        return lambdaUpdate().eq(ObjUtil.isNotNull(userProfile.getUserId()), UserProfile::getUserId, userProfile.getUserId()).update(userProfile);

    }

    @Override
    public IPage<UserProfilePageVO> getRankList(RankPageParam param) {

        Page<UserProfilePageVO> page = new Page<>();
        OrderItem orderItem = new OrderItem();

        switch (param.getType()) {
            case RANK_PLAYER_SCORE -> orderItem.setColumn("player_score");
            case RANK_ACHIEVEMENT_SCORE -> orderItem.setColumn("achievement");
            case RANK_LEARNING_PROGRESS -> orderItem.setColumn("learning_progress");
            case RANK_BOSS_1 -> orderItem.setColumn("boss1");
            case RANK_BOSS_2 -> orderItem.setColumn("boss2");
            case RANK_BOSS_3 -> orderItem.setColumn("boss3");
            case RANK_BOSS_4 -> orderItem.setColumn("boss4");
            case RANK_BOSS_5 -> orderItem.setColumn("boss5");
            case RANK_BOSS_6 -> orderItem.setColumn("boss6");
            case RANK_BOSS_7 -> orderItem.setColumn("boss7");
            default -> throw new ApiException(ResultCode.PARAMS_ERROR);
        }
        orderItem.setAsc(false);
        page.setCurrent(param.getCurrent()).setSize(param.getPageSize()).addOrder(orderItem);

        IPage<UserProfilePageVO> result = null;

        switch (param.getType()) {
            case RANK_PLAYER_SCORE -> result = baseMapper.pagePlayerScore(page, new QueryWrapper<UserProfilePageVO>().gt("player_score", 0));
            case RANK_ACHIEVEMENT_SCORE -> result = baseMapper.pageAchievement(page, new QueryWrapper<UserProfilePageVO>().gt("achievement", 0));
            case RANK_LEARNING_PROGRESS -> result = baseMapper.pageLearning(page, new QueryWrapper<UserProfilePageVO>().gt("learning_progress", 0));
            case RANK_BOSS_1 -> result = baseMapper.pageBoss1(page, new QueryWrapper<UserProfilePageVO>().gt("boss1", 0));
            case RANK_BOSS_2 -> result = baseMapper.pageBoss2(page, new QueryWrapper<UserProfilePageVO>().gt("boss2", 0));
            case RANK_BOSS_3 -> result = baseMapper.pageBoss3(page, new QueryWrapper<UserProfilePageVO>().gt("boss3", 0));
            case RANK_BOSS_4 -> result = baseMapper.pageBoss4(page, new QueryWrapper<UserProfilePageVO>().gt("boss4", 0));
            case RANK_BOSS_5 -> result = baseMapper.pageBoss5(page, new QueryWrapper<UserProfilePageVO>().gt("boss5", 0));
            case RANK_BOSS_6 -> result = baseMapper.pageBoss6(page, new QueryWrapper<UserProfilePageVO>().gt("boss6", 0));
            case RANK_BOSS_7 -> result = baseMapper.pageBoss7(page, new QueryWrapper<UserProfilePageVO>().gt("boss7", 0));
            default -> throw new ApiException(ResultCode.FAILED);
        }
        return result;
    }

}




