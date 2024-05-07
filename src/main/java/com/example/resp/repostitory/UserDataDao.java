package com.example.resp.repostitory;

import com.example.resp.entity.UserData;
import com.example.resp.repostitory.projuection.UserDatanameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDataDao extends JpaRepository<UserData, Long> {
    //服务器服务接口

    //通过userid获取相同userid下全部的用户数据的名字dataname
    List<UserDatanameProjection> findByUserid(Long userid);


    //通过userid，获取所有数据
    List<UserData> findAllByUserid(Long userid);

    //删除数据
    void deleteByUseridAndDataid(Long userid, Long dataid);

    //重命名-通过id查找到对象，JpaRepository更新时，没有提供update，而是通过save与存入数据库一样，不过当存入的对象拥有主键时，会对该主键拥有的数据进行更新而不是另存
    UserData findByDataid(Long dataid);
}
