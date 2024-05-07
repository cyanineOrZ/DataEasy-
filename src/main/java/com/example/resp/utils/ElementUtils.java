package com.example.resp.utils;

import com.example.resp.entity.ElementBase;
import com.example.resp.entity.ElementData;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ElementUtils {
    /**
     * 合并得到的控件基础属性和控件详细属性
     * @param baseList 控件基础属性列表
     * @param dataList 控件详细属性列表
     * @return 返回一个合并后的控件数据，里面包含了控件基本属性和控件的详细属性
     * */
    public static List<ElementBase> MergeElementBaseAndElementData(List<ElementBase> baseList, List<ElementData> dataList) {
        //遍历基础属性list， 将元素提取出来
        for (ElementBase elementBase : baseList) {
            //遍历详细属性list，将元素提取出来
            for (ElementData elementData : dataList) {
                //当基础属性的list的elementid等于详细属性的elementid时，将详细属性放入基础属性对象中
                if (elementBase.getElementid() == elementData.getElementid()) {
                    elementBase.setData(elementData.getData());
                }
            }
        }
        return baseList;
    }

    /***/
    public static List<Integer> deleteListToListINT(String deleteList) {

        List<Integer> resultList = new ArrayList<>();
        //使用正则表达式，进行匹配
        Pattern pattern = Pattern.compile("\\d+");

        Matcher matcher = pattern.matcher(deleteList);
        while (matcher.find()) {
            String match = matcher.group();
            resultList.add(Integer.parseInt(match));
        }

        return resultList;
    }

}
