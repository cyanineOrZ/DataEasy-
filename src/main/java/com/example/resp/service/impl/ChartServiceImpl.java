package com.example.resp.service.impl;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.example.resp.entity.ChartData;
import com.example.resp.repostitory.ChartDao;
import com.example.resp.service.ChartService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChartServiceImpl implements ChartService {
    //数据源
    @Resource
    private ChartDao chartDao;

    /**
     * 实现查找特定的用户的全部项目数据的事务
     * */
    @Override
    public List<ChartData> getAllChartDataService(Long userid) {
        //通过userid查找所有的chartDat
        return chartDao.findChartDataByUserid(userid);
    }


    /**
     * 实现getChartDataService
     * 通过用户id和chartid精确查找
     * @param userid 用户id
     * @param chartid 图标id
     * */
    @Override
    public ChartData getChartDataService(Long userid, Long chartid) {
        return chartDao.findByUseridAndChartid(userid, chartid);
    }

    /**
     * 实现重命名查找画布数据对象事务
     * */
    @Override
    public ChartData renameChartTitleService(Long chartid) {
        return chartDao.findByChartid(chartid);
    }

    /**
     * 实现查找需要上传图片的数据对象
     * */
    @Override
    public ChartData uploadChartCoverService(Long chartid) {
        return chartDao.findByChartid(chartid);
    }


    /**
     * 实现删除项目事务-查找需要删除的数据对象
     */
    @Override
    @Transactional
    public void deleteChartDataService(Long userid, Long chartid) {
        chartDao.deleteByUseridAndChartid(userid, chartid);
    }




}
