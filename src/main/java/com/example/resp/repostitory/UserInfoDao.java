package com.example.resp.repostitory;

import com.example.resp.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoDao extends JpaRepository<UserInfo, Long> {
    //通过userid查找用户资料对象
    UserInfo findByUserid(Long userid);
}
