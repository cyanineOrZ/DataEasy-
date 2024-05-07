package com.example.resp.entity;

import com.example.resp.entity.embed.ElementUniteKey;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "elementdata")
@IdClass(ElementUniteKey.class)
public class ElementData {
    @Id
    private Long chartid;
    @Id
    private int elementid;
    private String data;

    public ElementData() {}
    public ElementData(Long chartid, int elementid, String data) {
        this.chartid = chartid;
        this.elementid = elementid;
        this.data = data;
    }

    public Long getChartid() {return chartid;}public void setChartid(Long chartid) {this.chartid = chartid;}
    public int getElementid() {return elementid;}public void setElementid(int elementid) {this.elementid = elementid;}
    public String getData() {return data;}public void setData(String data) {this.data = data;}

    @Override
    public String toString() {
        return "ElementData{" +
                "chartid=" + chartid +
                ", elementid=" + elementid +
                ", data='" + data + '\'' +
                '}';
    }
}
