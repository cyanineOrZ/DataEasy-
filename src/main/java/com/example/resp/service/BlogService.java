package com.example.resp.service;

import com.example.resp.entity.Blog;

import java.util.List;

public interface BlogService {
    /**
     * 查找全部博客事务
     * @param userid 用户id
     * @return 用户的全部博客
     * */
    List<Blog> getUserBlog(Long userid);

    /**
     * 保存博客服务
     * @param blog 接收到的blog对象
     * @return 返回需要保存的对象，也就是接收到的对象
     * */
    Blog setUserBlog(Blog blog);

    /**
     * 删除博客服务
     * @param blogid 传入的blogid，用于搜寻需要进行删除的blog
     * */
    void deleteUserBlogService(Long userid, Long blogid);

    /**
     * 编辑博客服务
     * @param userid 传入的用户id
     * @param blogid 传入的博客id
     * @return 需要修改的Blog对象
     * */
    Blog editUserBlogService(Long userid, Long blogid);

}
