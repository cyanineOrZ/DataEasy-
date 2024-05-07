package com.example.resp.service;

import com.example.resp.entity.ChartData;

import java.util.List;

public interface ChartService {
    /**
     * 查找图表事务
     * @param userid 传入的userid，传入的用户id
     * @return List<ChartData>用户画布数据列表
     * */
    List<ChartData> getAllChartDataService (Long userid);

    /**
     * 重命名图表数据事务
     * @param chartid 传入的图表数据id，用于搜寻图标对象
     * */
    ChartData renameChartTitleService(Long chartid);

    /**
     * 上传图片事务
     * @param chartid 传入图表数据的id，用于搜寻图表数据的对象以添加图片或者删除图标对象
     * */
    ChartData uploadChartCoverService(Long chartid);

    /**
     * 通过用户id和chartid精确查找
     * @param userid 用户id
     * @param chartid 图标id
     * */
    ChartData getChartDataService(Long userid, Long chartid);

    /**
     * 删除事务
     * @param userid 用户唯一标识
     * @param chartid 画布唯一标识
     * */
    void deleteChartDataService(Long userid, Long chartid);
}
