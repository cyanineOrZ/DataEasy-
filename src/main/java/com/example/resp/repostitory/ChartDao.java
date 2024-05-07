package com.example.resp.repostitory;

import com.example.resp.entity.ChartData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChartDao extends JpaRepository<ChartData, Long> {

    /**
     * 通过userid获取全部用户的chartData数据(仅画布基础数据，用于项目管理页面)
     * @param userid 用户唯一标识
     * @return 返回画布基础数据列表
     * */
    List<ChartData> findChartDataByUserid(Long userid);

    /**
     * 通过chartid查找单个画布的数据，用于画布基础数据(仅画布基础数据，用于项目管理页面)的编辑(更换封面（uploadChartCover），重命名项目（renameChartTitle），画布数据的保存（saveChart）)
     * @param chartid 画布唯一标识
     * @return 需要进行修改的画布数据对象(仅画布基础数据，用于项目管理页面)
     * */
    ChartData findByChartid(Long chartid);


    /**
     * 通过userid和chartid查找画布数据对象，用于画布数据(仅画布基础数据，用于项目管理页面)的精确寻找，返回单个画布的基础数值
     * @param userid 用户唯一标识
     * @param chartid 画布唯一标识
     * @return 返回查找到的画布数据对象
     * */
    ChartData findByUseridAndChartid(Long userid, Long chartid);

    /**
     * 通过userid和chartid查找画布数据对象，用于画布数据(仅画布基础数据，用于项目管理页面)的精确寻找(删除画布(deleteChart))
     * @param userid 用户唯一标识
     * @param chartid 画布唯一标识
     * */
    void deleteByUseridAndChartid(Long userid, Long chartid);

}
