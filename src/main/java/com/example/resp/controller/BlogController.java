package com.example.resp.controller;

import com.example.resp.entity.Blog;
import com.example.resp.repostitory.BlogDao;
import com.example.resp.response.Response;
import com.example.resp.service.BlogService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Resource
    private BlogService blogService;
    @Resource
    private BlogDao blogDao;

    //请求该用户的所有博客内容
    @PostMapping("/getUserBlog")
    public Response<List<Blog>> getUserBlogController(@RequestParam Long userid) {

        List<Blog> blogList = blogService.getUserBlog(userid);

        return Response.success(blogList, "用户博客数据请求成功");
    }

    //增加博客
    @PostMapping("/addBlog")
    public Response<Blog> addBlogController(@RequestBody Blog newblog) {
        if (newblog != null) {
            Blog blog = blogService.setUserBlog(newblog);
            return Response.success(blog, "该博客保存成功");
        }
        else {
            return Response.error("999", "发送的博客为空，请检查");
        }

    }

    //删除博客
    @PostMapping("/deleteBlog")
    public Response deleteUserBlogController(@RequestParam Long userid, @RequestParam Long blogid) {
        blogService.deleteUserBlogService(userid, blogid);
        return Response.success();
    }

    //修改博客内容
    @PostMapping("/editBlog")
    public Response<Blog> editUserBlogController(@RequestBody Blog newBlog) {
        Blog blog = blogService.editUserBlogService(newBlog.getUserid(), newBlog.getBlogid());

        blog.setTitle(newBlog.getTitle());
        blog.setContent(newBlog.getContent());

        blogDao.save(blog);

        return Response.success(blog, "博客修改成功");
    }
}
