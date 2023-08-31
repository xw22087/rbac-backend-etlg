package com.imyuanxiao.rbac.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imyuanxiao.rbac.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imyuanxiao.rbac.model.vo.UserProfilePageVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author Administrator
* @description 针对表【user(user table)】的数据库操作Mapper
* @createDate 2023-08-03 16:57:55
* @Entity com.imyuanxiao.rbac.model.entity.User
*/
@Repository
public interface UserMapper extends BaseMapper<User> {

    IPage<UserProfilePageVO> selectRankList(Page<UserProfilePageVO> page, @Param(Constants.WRAPPER) Wrapper<UserProfilePageVO> wrapper);

}




