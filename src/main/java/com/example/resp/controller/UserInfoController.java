package com.example.resp.controller;

import com.example.resp.entity.UserInfo;
import com.example.resp.repostitory.UserInfoDao;
import com.example.resp.response.Response;
import com.example.resp.service.UserInfoService;
import com.example.resp.utils.FileUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/userinfo")
public class UserInfoController {
    //头像默认值
    private final String defaultAvatarPicture = "http://localhost:8080/file/userAvatar/defaultAvatar.jpg";
    //背景默认值
    private final String defaultBackgroundPicture = "http://localhost:8080/file/blogHeadPicture/defaultbg.png";

    //服务源
    @Resource
    private UserInfoService userInfoService;
    //数据源
    @Resource
    private UserInfoDao userInfoDao;


    //获取用户所有信息
    @PostMapping("/getUserInfo")
    public Response<UserInfo> getUserHeadPictureController(@RequestParam Long userid) {
        UserInfo userInfo = userInfoService.getUserHeadPictureService(userid);
        if (userInfo != null) {
            return Response.success(userInfo, "获取用户信息成功");
        }
        else {
            return Response.error("999", "用户信息错误");
        }
    }

    //更改用户信息
    @PostMapping("/updateUserInfo")
    public Response<UserInfo> updateUserInfoController(@RequestBody UserInfo userInfo) {

        //确保不改变背景图片和头像,通过获取到的整个对象的id找到旧对象
        UserInfo oldUserInfo = userInfoService.getUserInfoService(userInfo.getUserid());
        //设置新对象的头像和头图片为旧对象的头像和头图片（一般这两个数据通过这个接口传上来的，都是空的）
        userInfo.setUseravatarpicture(oldUserInfo.getUseravatarpicture());
        userInfo.setUserblogheadpicture(oldUserInfo.getUserblogheadpicture());

        //更新数据
        userInfoDao.save(userInfo);
        return Response.success(userInfo, "用户数据编辑成功");
    };

    //上传头像
    @PostMapping("/uploadUserAvatar")
    public Response<UserInfo> uploadUserAvatarController(@RequestParam Long userid, @RequestParam("file") MultipartFile file) {

        String timestamp = String.valueOf(System.currentTimeMillis());

        //获取最终文件path
        String filePath = FileUtils.createFilePath("userAvatar", file, timestamp);

        //存入文件
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //找到需要上传图片的用户对象
        UserInfo userInfo = userInfoService.uploadHeadPictureService(userid);

        //删除旧文件
        FileUtils.removeOldFile(userInfo, "useravatarpicture", userInfo.getUseravatarpicture(), defaultAvatarPicture);

        //生成新文件URL
        String imageURL = FileUtils.createURL("userAvatar", file, timestamp);

        //存入新文件的URL
        userInfo.setUseravatarpicture(imageURL);
        userInfoDao.save(userInfo);

        return Response.success(userInfo, "上传头像成功");

    }


    //上传博客头图片和个人资料背景图
    @PostMapping("/uploadHeadPicture")
    public Response<UserInfo> uploadHeadPictureController(@RequestParam Long userid, @RequestParam("file") MultipartFile file) {

        //生成时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        //获取最终文件path
        String filePath = FileUtils.createFilePath("blogHeadPicture", file, timestamp);

        //存入文件
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //找到需要上传图片的用户对象
        UserInfo userInfo = userInfoService.uploadHeadPictureService(userid);

        //删除旧文件
        FileUtils.removeOldFile(userInfo, "blogHeadPicture", userInfo.getUserblogheadpicture(), defaultBackgroundPicture);

        //生成URL
        String imageURL = FileUtils.createURL("blogHeadPicture", file, timestamp);

        //保存数据
        userInfo.setUserblogheadpicture(imageURL);
        userInfoDao.save(userInfo);

        return Response.success(userInfo, "更换头图片成功");
    }
}
