package com.example.resp.controller;

import com.example.resp.entity.User;
import com.example.resp.response.Response;
import com.example.resp.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class UserController {

    // 服务源
    @Resource
    private UserService userService;

    //注册用户
    @PostMapping("/register")
    public Response<User> registerController(@RequestBody User newUser) {
        User user = userService.registerService(newUser);
        if (user != null) {
            return Response.success(user, "注册成功");
        }
        else {
            return Response.error("999", "用户名已存在");
        }
    }

    //登录
    @PostMapping("/login")
    //接收获取到的数据
    public Response<User> loginController(@RequestParam String account, @RequestParam String password) {
        //对接收到的数据进行检验，如果账号为空，则返回状态码555，信息？
        if (account.equals("")) {
            return Response.error("555", "?");
        }
        //通过UserService接口的loginService方法(也就是调用这个接口的实现类里面的loginService的方法，传入一些参数)
        User user = userService.loginService(account, password);
        if (user != null) {
            return Response.success(user, "登录成功");
        }else {
            return Response.error("123", "账号或密码错误");
        }
    }
}
