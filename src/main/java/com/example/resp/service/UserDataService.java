package com.example.resp.service;

import com.example.resp.entity.UserData;

import java.util.List;

public interface UserDataService {
    /***
     * 上传用户数据事务
     * @param userData 接收到的请求体
     * @return 返回一个UserData对象
     */
    UserData setUserDataService (UserData userData);


    /**
     * 查找用户全部数据事务
     * @param userid 传入的userid， 传入的用户id， 用以寻找对应的用户拥有的数据
     * @return List<UserData>用户上传的数据的列表
     */
    List<UserData> getUserDataService (Long userid);

    /**
     * 删除用户数据服务
     * @param userid 用户id
     * @param dataid 用户数据id
     * */
    void deleteUserDataService(Long userid, Long dataid);

    /**
     * 重命名用户数据
     * @param dataid 用户数据id——主键
     * */
    UserData renameUserDataService(Long dataid);
}
