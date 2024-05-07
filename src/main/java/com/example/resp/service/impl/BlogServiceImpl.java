package com.example.resp.service.impl;

import com.example.resp.entity.Blog;
import com.example.resp.repostitory.BlogDao;
import com.example.resp.response.Response;
import com.example.resp.service.BlogService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    //数据源
    @Resource
    private BlogDao blogDao;

    //实现通过用户id查找用户所有的博客
    @Override
    public List<Blog> getUserBlog(Long userid) {
        return blogDao.findByUserid(userid);
    }

    //实现保存用户新发表的博客
    @Override
    public Blog setUserBlog(Blog blog) {
        return blogDao.save(blog);
    }

    //实现博客对象的删除
    @Override
    @Transactional
    public void deleteUserBlogService(Long userid, Long blogid) {
        blogDao.deleteByUseridAndBlogid(userid, blogid);
    }

    //实现博客对象的修改,返回需要修改的Blog对象
    @Override
    public Blog editUserBlogService(Long userid, Long blogid) {
        return blogDao.findByUseridAndBlogid(userid, blogid);
    }
}
