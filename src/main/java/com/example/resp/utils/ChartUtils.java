package com.example.resp.utils;

import com.example.resp.entity.ChartData;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ChartUtils {
    /**
     * 由于传进来的值会有null，这里要将数据库内的提取出来，并且进行赋值，以期更新的时候不会受到null值的影响
     * */
    public static ChartData uploadExpectNull(ChartData newValueChartData, ChartData oldValueChartData) {
        //反射实在是写不出来

        //封面图片，标题 -- 一定为上次保存的值
        newValueChartData.setCoverimage(oldValueChartData.getCoverimage());

        newValueChartData.setTitle(oldValueChartData.getTitle());

        //背景图片
        if (newValueChartData.getBackgroundimage() == null) {
            newValueChartData.setBackgroundimage(oldValueChartData.getBackgroundimage());
        }


        return newValueChartData;
    }

}
