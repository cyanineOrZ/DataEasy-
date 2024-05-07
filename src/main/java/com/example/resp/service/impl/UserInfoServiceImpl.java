package com.example.resp.service.impl;

import com.example.resp.entity.UserInfo;
import com.example.resp.repostitory.UserInfoDao;
import com.example.resp.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo getUserInfoService(Long userid) {
        return userInfoDao.findByUserid(userid);
    }

    //实现用户上传头图片事务，查找需要上传的数据对象
    @Override
    public UserInfo uploadHeadPictureService(Long userid) {
        return userInfoDao.findByUserid(userid);
    }

    //实现查找用户头图片的事务
    @Override
    public UserInfo getUserHeadPictureService(Long userid) {
        return userInfoDao.findByUserid(userid);
    }


}
