package com.example.resp.repostitory;

import com.example.resp.entity.ElementBase;
import com.example.resp.entity.ElementData;
import com.example.resp.entity.embed.ElementUniteKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ElementBaseDao extends JpaRepository<ElementBase, ElementUniteKey> {

    //通过chartid查找全部控件的基本属性
    List<ElementBase> findByChartid(Long chartid);

    //精确查找
    ElementBase findByChartidAndElementid(Long chartid, int elementid);

    //删除某个控件基础数据
    void deleteByChartidAndElementid(Long chartid, int elementid);

}
