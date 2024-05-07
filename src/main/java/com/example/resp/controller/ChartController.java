package com.example.resp.controller;

import com.example.resp.entity.ChartData;
import com.example.resp.entity.ElementBase;
import com.example.resp.entity.ElementData;
import com.example.resp.repostitory.ChartDao;
import com.example.resp.repostitory.ElementBaseDao;
import com.example.resp.repostitory.ElementDataDao;
import com.example.resp.response.Response;
import com.example.resp.service.ChartService;
import com.example.resp.service.ElementBaseService;
import com.example.resp.service.ElementDataService;
import com.example.resp.utils.ChartUtils;
import com.example.resp.utils.ElementUtils;
import com.example.resp.utils.FileUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/chart")
public class ChartController {

    //服务源
    @Resource
    private ChartService chartService;

    @Resource
    private ElementDataService elementDataService;
    @Resource
    private ElementBaseService elementBaseService;

    //数据源
    @Resource
    private ChartDao chartDao;
    @Resource
    private ElementBaseDao elementBaseDao;
    @Resource
    private ElementDataDao elementDataDao;

    //请求用户的全部图表数据
    @PostMapping("/getChartData")
    public Response<List<ChartData>> getUserAllChartDataController(@RequestParam Long userid) {

        List<ChartData> chartDataList = chartService.getAllChartDataService(userid);

        return Response.success(chartDataList, "用户图表数据获取成功");
    }

    //查找单个项目,但不寻找项目的全部数据
    @PostMapping("/getCurrentChartData")
    public Response<ChartData> getCurrentChartDataController(@RequestParam Long userid, @RequestParam Long chartid) {
        ChartData chartData = chartService.getChartDataService(userid, chartid);
        return Response.success(chartData, "获取数据");
    }

    //查找单个项目以及全部控件内容
    @PostMapping("/getAllCurrentChartData")
    public Response<ChartData> getAllCurrentChartDataController(@RequestParam Long userid, @RequestParam Long chartid) {

        //查找需要找到的数据对象-项目
        ChartData chartData = chartService.getChartDataService(userid, chartid);
        //获取控件基础信息list
        List<ElementBase> elementBaseList = elementBaseService.getAllCurrentChartElementBaseService(chartid);
        //获取控件详细信息list
        List<ElementData> elementDataList = elementDataService.getAllCurrentChartElementDataService(chartid);
        //进行控件信息的组合
        List<ElementBase> res = ElementUtils.MergeElementBaseAndElementData(elementBaseList, elementDataList);

        chartData.setElements(res);

        return Response.success(chartData, "控件数据");
    }

    //重命名
    @PostMapping("/renameChartTitle")
    public Response<ChartData> renameChartTitleController(@RequestParam Long chartid, @RequestParam String reTitle) {
        ChartData chartData = chartService.renameChartTitleService(chartid);
        if (reTitle != null) {
            chartData.setTitle(reTitle);
            chartDao.save(chartData);
            return Response.success(chartData, "重命名成功");
        }
        else {
            return Response.error("999", "重命名失败，重命名值不得为空");
        }
    }

    //上传封面
    @PostMapping("/uploadChartCover")
    public Response uploadChartCoverController(@RequestParam Long chartid, @RequestParam("file") MultipartFile file) {

        //生成时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        String filePath = FileUtils.createFilePath("chartCover", file, timestamp);

        //存入文件
        try {
            file.transferTo(new File(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //根据传入的chartid获取需要存储图片的图表数据对象
        ChartData chartData = chartService.uploadChartCoverService(chartid);

        //删除旧文件
        FileUtils.removeOldFile(chartData, "coverimage", chartData.getCoverimage());

        //生成url
        String imageUrl = FileUtils.createURL("chartCover", file, timestamp);


        chartData.setCoverimage(imageUrl);
        chartDao.save(chartData);

        return Response.success(chartData, "成功上传封面");
    }

    //添加项目
    @PostMapping("/creatChart")
    public Response<ChartData> creatChartDataController(@RequestParam Long userid, @RequestParam("file") MultipartFile file, @RequestParam String title) {
        //生成时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());
        //path
        String filePath = FileUtils.createFilePath("chartCover", file, timestamp);
        //存入文件
        try {
            file.transferTo(new File(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //生成url
        String imageUrl = FileUtils.createURL("chartCover", file, timestamp);
        //设置新项目
        ChartData chartData = new ChartData();
        chartData.setUserid(userid);
        chartData.setCoverimage(imageUrl);
        chartData.setTitle(title);
        chartDao.save(chartData);
        return Response.success(chartData, "创建项目成功");
    }

    //删除项目
    @PostMapping("/deleteChart")
    public Response deleteChartDataController(@RequestParam Long userid, @RequestParam Long chartid) {
        //查找需要删除的数据对象,用来删除封面图片
        ChartData chartData = chartService.getChartDataService(userid, chartid);
        //删除封面文件
        FileUtils.removeOldFile(chartData, "coverimage", chartData.getCoverimage());
        //最终删除
        chartService.deleteChartDataService(userid, chartid);
        return null;
    }

    //编辑项目——画布数据保存
    @PostMapping("/saveChart")
    public Response<ChartData> saveChartDataController(@RequestBody ChartData newChartData) {
        System.out.println("画布详细信息保存");
        //找到旧的数据对象，将不需要更换的数据set进新数据对象中
        ChartData oldChartData = chartService.getChartDataService(newChartData.getUserid(), newChartData.getChartid());
        //set新的数据对象
        ChartData resultChartData = ChartUtils.uploadExpectNull(newChartData, oldChartData);

        chartDao.save(resultChartData);
        return null;
    }

    //编辑项目——画布背景图片保存
    @PostMapping("/saveChartBackgroundImage")
    public Response<String> saveChartBackgroundImageController(@RequestParam Long userid, @RequestParam Long chartid, @RequestParam("file") MultipartFile file) {

        //生成时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        //生成path
        String filePath = FileUtils.createFilePath("chartBackgroundImage", file, timestamp);

        //存入文件
        try {
            file.transferTo(new File(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //根据传入的chartid获取需要存储图片的图表数据对象
        ChartData chartData = chartService.getChartDataService(userid, chartid);

        //删除旧文件
        FileUtils.removeOldFile(chartData, "backgroundimage", chartData.getBackgroundimage());

        //生成url
        String imageUrl = FileUtils.createURL("chartBackgroundImage", file, timestamp);

        //更新数据
        chartData.setBackgroundimage(imageUrl);
        chartDao.save(chartData);

        return Response.success(imageUrl, "上传的图片的url");
    }

    //编辑项目——图片控件图片文件上传
    @PostMapping("/saveImageElementImage")
    public Response<String> saveChartImageElementImageController(@RequestParam Long chartid, @RequestParam int elementid, @RequestParam("file") MultipartFile file) {

        //生成时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());

        //生成上传对象的Path
        String newImagePath = FileUtils.createFilePath("imageElementImage", file, timestamp);

        //存入文件
        try {
            file.transferTo(new File(newImagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //找到需要替换图像或者上传图像的数据对象
        ElementData elementData = elementDataDao.findByChartidAndElementid(chartid, elementid);

        String oldData = elementData.getData();

        //生成新图片的URL
        String imageUrl = FileUtils.createURL("imageElementImage", file, timestamp);

        //删除旧文件
        String newData = FileUtils.changeImageElementPicture(oldData, imageUrl);

        elementData.setData(newData);
        elementDataDao.save(elementData);


        return Response.success(newData, "图片更改成功");
    }

    //编辑项目——控件基础数据保存
    @PostMapping("/saveElementBase")
    public Response<ElementBase> saveChartDataElementBaseController(@RequestBody List<ElementBase> elementBaseDataList) {
        //  取消活跃
        for (ElementBase elementBase: elementBaseDataList) {
            elementBase.setActive(false);
        }
        System.out.println("控件基础信息保存");
        elementBaseDao.saveAll(elementBaseDataList);
        return null;

//        BEGIN
//        INSERT INTO elementdata(chartid, elementid) VALUES (NEW.chartid, NEW.elementid);
//        END
    }

    //编辑项目——控件详细数据保存
    @PostMapping("/saveElementData")
    public Response<ElementData> saveChartDataElementDataController(@RequestBody List<ElementData> elementDataList) {
        System.out.println("控件详细信息保存");
        elementDataDao.saveAll(elementDataList);
        return null;
    }

    //编辑项目——删除队列中的控件
    @PostMapping("/deleteElement")
    public void deleteChartDataElementController(@RequestParam Long chartid, @RequestParam String deleteElementIdList) {
        System.out.println(deleteElementIdList);

        List<Integer> test = ElementUtils.deleteListToListINT(deleteElementIdList);

        System.out.println(test);

        for (Integer elementid: test) {
            elementBaseService.deleteChartElementBaseService(chartid, elementid);
            elementDataService.deleteChartElementDataService(chartid, elementid);
        }

    }
}
