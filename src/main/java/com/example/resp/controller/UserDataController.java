package com.example.resp.controller;

import com.example.resp.entity.User;
import com.example.resp.entity.UserData;
import com.example.resp.repostitory.UserDataDao;
import com.example.resp.response.Response;
import com.example.resp.service.UserDataService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/userdata")
public class UserDataController {

    //服务源
    @Resource
    private UserDataService userDataService;
    @Resource
    private UserDataDao userDataDao;

    //
    @PostMapping("/getUserData")
    public Response<List<UserData>> getUserDataController(@RequestParam Long userid) {

        //传入接收到的userid给具体服务的实现类UserDataServiceImpl 获取结果
        List<UserData> userDataList = userDataService.getUserDataService(userid);

        if (userDataList == null) {
            return Response.error("001", "没有数据");
        }
        else {
            return Response.success(userDataList, "查询成功");
        }
    };

    //保存用户数据
    @PostMapping("/setUserData")
    public Response<UserData> setUserDataController(@RequestBody UserData newUserData) {
        UserData userData = userDataService.setUserDataService(newUserData);
        if (userData != null) {
            return Response.success(userData, "保存成功");
        }
        else {
            return Response.error("999", "保存失败");
        }
    }

    //删除用户数据
    @PostMapping("/deleteUserData")
    public Response deleteUserDataController(@RequestParam Long userid, @RequestParam Long dataid) {
        userDataService.deleteUserDataService(userid, dataid);
        return Response.success();
    }

    //重命名用户数据
    @PostMapping("/renameUserData")
    public Response renameUserDataController(@RequestParam Long dataid, @RequestParam String rename) {
        //这里拿到主键拥有的数据
        UserData userData = userDataService.renameUserDataService(dataid);
        if(rename != null) {
            userData.setDataname(rename);
            userDataDao.save(userData);
            return Response.success(userData, "重命名成功");
        }
        else {
            return Response.error("930", "rename为空");
        }
    }
}
