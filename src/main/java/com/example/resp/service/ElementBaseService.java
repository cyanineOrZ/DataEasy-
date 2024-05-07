package com.example.resp.service;

import com.example.resp.entity.ElementBase;

import java.util.List;

public interface ElementBaseService {



    /**
     * 查找一个画布下的全部控件事务
     * */
    List<ElementBase> getAllCurrentChartElementBaseService(Long chartid);

    /***/
    void deleteChartElementBaseService(Long chartid, int elementid);
}
