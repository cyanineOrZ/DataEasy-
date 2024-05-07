package com.example.resp.entity;


import com.example.resp.entity.embed.ElementUniteKey;
import jakarta.persistence.*;

@Entity
@Table(name = "element")
@IdClass(ElementUniteKey.class)
public class ElementBase {
    @Id
    private Long chartid;
    @Id
    private int elementid;
    private String type;
    private String name;
    private int width;
    private int height;
    private int top;
    private int rightresever;
    private String backgroundcolor;
    private Boolean active;

    @Transient
    private String data;


    public ElementBase() {
    }

    public ElementBase(Long chartid, int elementid, String name, String type, Boolean active, int width, int height, int top, int rightresever, String backgroundcolor) {
        this.chartid = chartid;
        this.elementid = elementid;
        this.name = name;
        this.type = type;
        this.active = active;
        this.width = width;
        this.height = height;
        this.top = top;
        this.rightresever = rightresever;
        this.backgroundcolor = backgroundcolor;
    }

    public Long getChartid() {
        return chartid;
    }

    public void setChartid(Long chartid) {
        this.chartid = chartid;
    }

    public int getElementid() {
        return elementid;
    }

    public void setElementid(int elementid) {
        this.elementid = elementid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRightresever() {
        return rightresever;
    }

    public void setRightresever(int rightresever) {
        this.rightresever = rightresever;
    }

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ElementBase{" +
                "chartid=" + chartid +
                ", elementid=" + elementid +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", active=" + active +
                ", width=" + width +
                ", height=" + height +
                ", top=" + top +
                ", rightresever=" + rightresever +
                ", backgroundcolor='" + backgroundcolor + '\'' +
                '}';
    }
}
