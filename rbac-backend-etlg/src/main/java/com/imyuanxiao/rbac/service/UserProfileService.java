package com.imyuanxiao.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.imyuanxiao.rbac.model.entity.UserProfile;
import com.imyuanxiao.rbac.model.param.RankPageParam;
import com.imyuanxiao.rbac.model.vo.UserProfilePageVO;

/**
* @author Administrator
* @description 针对表【user_profile】的数据库操作Service
* @createDate 2023-08-03 16:52:43
*/
public interface UserProfileService extends IService<UserProfile> {
    UserProfile getByUserId(Long userId);

    boolean updateByUserId(UserProfile userProfile);

    IPage<UserProfilePageVO> getRankList(RankPageParam param);

   // void updateUserProfile(UserProfileParam param);
}
