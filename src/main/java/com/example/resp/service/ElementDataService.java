package com.example.resp.service;

import com.example.resp.entity.ElementData;

import java.util.List;

public interface ElementDataService {
    /**
     *
     * */
    List<ElementData> getAllCurrentChartElementDataService(Long chartid);


    /**
     *
     * */
    void deleteChartElementDataService(Long chartid, int elementid);
}
