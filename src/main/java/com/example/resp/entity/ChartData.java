package com.example.resp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "chartdata")
public class ChartData {
    private Long userid;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chartid;
    private String title;
    private String coverimage;
    private int width;
    private int height;
    private int top;
    private int rightresever;
    private String backgroundsetting;
    private String backgroundcolor;
    private String backgroundimage;
    private String backgroundimagesize;

    @Transient
    private List<ElementBase> elements;

    //构造函数
    public ChartData() {}

    public ChartData(Long userid, Long chartid, String title, String coverimage, int width, int height, int top, int rightresever, String backgroundsetting, String backgroundcolor, String backgroundimage, String backgroundimagesize) {
        this.userid = userid;
        this.chartid = chartid;
        this.title = title;
        this.coverimage = coverimage;
        this.width = width;
        this.height = height;
        this.top = top;
        this.rightresever = rightresever;
        this.backgroundsetting = backgroundsetting;
        this.backgroundcolor = backgroundcolor;
        this.backgroundimage = backgroundimage;
        this.backgroundimagesize = backgroundimagesize;
    }

    //getter和setter

    public Long getUserid() {
        return userid;
    }public void setUserid(Long userid) {
        this.userid = userid;
    }
    public Long getChartid() {
        return chartid;
    }public void setChartid(Long chartid) {
        this.chartid = chartid;
    }
    public String getTitle() {
        return title;
    }public void setTitle(String title) {
        this.title = title;
    }
    public String getCoverimage() {
        return coverimage;
    }public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
    }
    public int getWidth() {
        return width;
    }public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }public void setHeight(int height) {
        this.height = height;
    }
    public int getTop() {
        return top;
    }public void setTop(int top) {
        this.top = top;
    }
    public int getRightresever() {
        return rightresever;
    }public void setRightresever(int rightresever) {
        this.rightresever = rightresever;
    }
    public String getBackgroundsetting() {return backgroundsetting;}public void setBackgroundsetting(String backgroundsetting) {this.backgroundsetting = backgroundsetting;}
    public String getBackgroundcolor() {return backgroundcolor;}public void setBackgroundcolor(String backgroundcolor) {this.backgroundcolor = backgroundcolor;}
    public String getBackgroundimage() {return backgroundimage;}public void setBackgroundimage(String backgroundimage) {this.backgroundimage = backgroundimage;}
    public String getBackgroundimagesize() {return backgroundimagesize;}public void setBackgroundimagesize(String backgroundimagesize) {this.backgroundimagesize = backgroundimagesize;}
    public List<ElementBase> getElements() {
        return elements;
    }public void setElements(List<ElementBase> elements) {
        this.elements = elements;
    }

    @Override
    public String toString() {
        return "ChartData{" +
                "userid=" + userid +
                ", chartid=" + chartid +
                ", title='" + title + '\'' +
                ", coverimage='" + coverimage + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", top=" + top +
                ", rightresever=" + rightresever +
                ", backgroundsetting='" + backgroundsetting + '\'' +
                ", backgroundcolor='" + backgroundcolor + '\'' +
                ", backgroundimage='" + backgroundimage + '\'' +
                ", backgroundimagesize='" + backgroundimagesize + '\'' +
                ", elements=" + elements +
                '}';
    }
}
