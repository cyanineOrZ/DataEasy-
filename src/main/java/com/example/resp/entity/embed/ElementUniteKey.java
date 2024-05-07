package com.example.resp.entity.embed;
import java.io.Serializable;
import java.util.Objects;

public class ElementUniteKey implements Serializable {
    private Long chartid;
    private int elementid;

    public ElementUniteKey() {
    }

    public ElementUniteKey(Long chartid, int elementid) {
        this.chartid = chartid;
        this.elementid = elementid;
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

    @Override
    public String toString() {
        return "ElementUniteKey{" +
                "chartid=" + chartid +
                ", elementid=" + elementid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementUniteKey that = (ElementUniteKey) o;
        return elementid == that.elementid && chartid.equals(that.chartid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chartid, elementid);
    }
}
