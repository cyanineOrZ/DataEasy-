package com.example.resp.repostitory;

import com.example.resp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    //数据库服务接口， 这里使用jpa实现登录， Jpa
    User findByAccount(String account);

    User findByAccountAndPassword(String account, String password);
}
