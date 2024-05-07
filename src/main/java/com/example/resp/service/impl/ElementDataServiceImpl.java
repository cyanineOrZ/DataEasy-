package com.example.resp.service.impl;

import com.example.resp.entity.ElementData;
import com.example.resp.repostitory.ElementDataDao;
import com.example.resp.service.ElementDataService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElementDataServiceImpl implements ElementDataService {
    @Resource
    private ElementDataDao elementDataDao;


    @Override
    public List<ElementData> getAllCurrentChartElementDataService(Long chartid) {
        return elementDataDao.findByChartid(chartid);
    }

    @Override
    @Transactional
    public void deleteChartElementDataService(Long chartid, int elementid) {
        elementDataDao.deleteByChartidAndElementid(chartid, elementid);
    }
}
