package com.example.resp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "blog")
public class Blog {
    private Long userid;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogid;
    private String title;
    private String content;
    private String uploadtime;

    //构造函数
    public Blog() {}
    public Blog(Long userid, Long blogid, String title, String content, String uploadtime) {
        this.userid = userid;
        this.blogid = blogid;
        this.title = title;
        this.content = content;
        this.uploadtime = uploadtime;
    }

    //getter和setter
    public Long getUserid() {return userid;}public void setUserid(Long userid) {this.userid = userid;}
    public Long getBlogid() {return blogid;}public void setBlogid(Long blogid) {this.blogid = blogid;}
    public String getTitle() {return title;}public void setTitle(String title) {this.title = title;}
    public String getContent() {return content;}public void setContent(String content) {this.content = content;}
    public String getUploadtime() {return uploadtime;}public void setUploadtime(String uploadtime) {this.uploadtime = uploadtime;}
}
