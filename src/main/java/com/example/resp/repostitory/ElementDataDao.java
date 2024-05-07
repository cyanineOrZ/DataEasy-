package com.example.resp.repostitory;

import com.example.resp.entity.ElementData;
import com.example.resp.entity.embed.ElementUniteKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElementDataDao extends JpaRepository<ElementData, ElementUniteKey> {
    //测试
    List<ElementData> findByChartid(Long chartid);

    //精确查找某个控件
    ElementData findByChartidAndElementid(Long chartid, int elementid);

    //删除某个控件详细数据
    void deleteByChartidAndElementid(Long chartid, int elementid);
}
