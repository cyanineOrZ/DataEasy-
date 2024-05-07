package com.example.resp.entity;

import com.mysql.cj.jdbc.Blob;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "usersdata")
public class UserData {
    private Long userid;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dataid;
    private String dataname;
    private String sheetname;
    private String dimension;
    private String metrics;
    private String columns;
    private String exdata;
    private String uploadtime;

    //构造函数
    public UserData() {}
    public UserData(Long userid, Long dataid, String dataname, String sheetname, String dimension, String metrics, String columns, String exdata, String uploadtime) {
        this.userid = userid;
        this.dataid = dataid;
        this.dataname = dataname;
        this.sheetname = sheetname;
        this.dimension = dimension;
        this.metrics = metrics;
        this.columns = columns;
        this.exdata = exdata;
        this.uploadtime = uploadtime;
    }

    //getter和setter
    public Long getUserid() {return userid;}public void setUserid(Long userid) {this.userid = userid;}
    public Long getDataid() {return dataid;}public void setDataid(Long dataid) {this.dataid = dataid;}
    public String getDataname() {return dataname;}public void setDataname(String dataname) {this.dataname = dataname;}
    public String getSheetname() {return sheetname;}public void setSheetname(String sheetname) {this.sheetname = sheetname;}
    public String getDimension() {return dimension;}public void setDimension(String dimension) {this.dimension = dimension;}
    public String getMetrics() {return metrics;}public void setMetrics(String metrics) {this.metrics = metrics;}
    public String getColumns() {return columns;}public void setColumns(String columns) {this.columns = columns;}
    public String getExdata() {return exdata;}public void setExdata(String exdata) {this.exdata = exdata;}
    public String getUploadtime() {return uploadtime;}public void setUploadtime(String uploadtime) {this.uploadtime = uploadtime;}
}
