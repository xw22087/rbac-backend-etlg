package com.imyuanxiao.rbac.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.model.entity.UserProfile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imyuanxiao.rbac.model.vo.UserProfilePageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author Administrator
* @description 针对表【user_profile】的数据库操作Mapper
* @createDate 2023-08-03 17:14:35
* @Entity com.imyuanxiao.rbac.model.entity.UserProfile
*/
@Repository
public interface UserProfileMapper extends BaseMapper<UserProfile> {

    IPage<UserProfilePageVO> pagePlayerScore(Page<UserProfilePageVO> page, @Param(Constants.WRAPPER) Wrapper<UserProfilePageVO> wrapper);
    IPage<UserProfilePageVO> pageAchievement(Page<UserProfilePageVO> page, @Param(Constants.WRAPPER) Wrapper<UserProfilePageVO> wrapper);
    IPage<UserProfilePageVO> pageLearning(Page<UserProfilePageVO> page, @Param(Constants.WRAPPER) Wrapper<UserProfilePageVO> wrapper);
    IPage<UserProfilePageVO> pageBoss1(Page<UserProfilePageVO> page, @Param(Constants.WRAPPER) Wrapper<UserProfilePageVO> wrapper);
    IPage<UserProfilePageVO> pageBoss2(Page<UserProfilePageVO> page, @Param(Constants.WRAPPER) Wrapper<UserProfilePageVO> wrapper);
    IPage<UserProfilePageVO> pageBoss3(Page<UserProfilePageVO> page, @Param(Constants.WRAPPER) Wrapper<UserProfilePageVO> wrapper);
    IPage<UserProfilePageVO> pageBoss4(Page<UserProfilePageVO> page, @Param(Constants.WRAPPER) Wrapper<UserProfilePageVO> wrapper);
    IPage<UserProfilePageVO> pageBoss5(Page<UserProfilePageVO> page, @Param(Constants.WRAPPER) Wrapper<UserProfilePageVO> wrapper);
    IPage<UserProfilePageVO> pageBoss6(Page<UserProfilePageVO> page, @Param(Constants.WRAPPER) Wrapper<UserProfilePageVO> wrapper);
    IPage<UserProfilePageVO> pageBoss7(Page<UserProfilePageVO> page, @Param(Constants.WRAPPER) Wrapper<UserProfilePageVO> wrapper);

}




