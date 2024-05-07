package com.example.resp.repostitory;

import com.example.resp.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogDao extends JpaRepository<Blog, Long> {
    //通过用户id查找全部的Blog
    List<Blog> findByUserid(Long userid);

    //
    void deleteByUseridAndBlogid(Long userid, Long blogid);

    //
    Blog findByUseridAndBlogid(Long userid, Long blogid);
}
