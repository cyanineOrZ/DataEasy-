package com.example.resp.service.impl;

import com.example.resp.entity.ElementBase;
import com.example.resp.repostitory.ElementBaseDao;
import com.example.resp.service.ElementBaseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ElementBaseServiceImpl implements ElementBaseService {

    @Resource
    private ElementBaseDao elementBaseDao;


    @Override
    public List<ElementBase> getAllCurrentChartElementBaseService(Long chartid) {
        return elementBaseDao.findByChartid(chartid);
    }


    @Override
    @Transactional
    public void deleteChartElementBaseService(Long chartid, int elementid) {
        elementBaseDao.deleteByChartidAndElementid(chartid, elementid);
    }

}
