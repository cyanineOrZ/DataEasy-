package com.example.resp.service;


import com.example.resp.entity.UserInfo;

public interface UserInfoService {
    /**
     * 查找userInfo数据对象
     * @param userid 用户id，用来查找相对应的UserInfo对象
     * @return 需要进行更改的数据对象
     * */
    UserInfo getUserInfoService(Long userid);

    /**
     * 上传头图片事务
     * @param userid 用户id，用来查找相对应的UserInfo数据对象
     * @return 需要上传的数据对象
     * */
    UserInfo uploadHeadPictureService(Long userid);

    /**
     * 查找用户头图片事务
     * @param userid 用户id， 用来查找相对应的UserInfo数据对象
     * @return UserInfo 返回查找到的数据对象
     * */
    UserInfo getUserHeadPictureService(Long userid);
}
