package com.example.resp.utils;

import com.example.resp.entity.UserInfo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class FileUtils {
    /**
     * 获取一个文件的完整path，用于存储
     * @param folderName 文件存储的文件夹名称，file下的文件夹名称即可
     * @param file 需要获取filepath的文件
     * @param timestamp 外部生成的时间戳，一定要外部生成，避免与URL不一样
     * @return 返回一个用于存储文件的最终path
     * */
    public static String createFilePath(String folderName, MultipartFile file, String timestamp) {
        //创建一个基础Path，记录文件夹位置
        String BasePath = "D:\\rs\\resp\\file\\" + folderName;
        //新建文件夹对象
        File directory = new File(BasePath);
        //获取上传的图片的原始文件名
        String originalFilename  = file.getOriginalFilename();

        //最终文件path
        return BasePath + File.separator + timestamp + "_" + originalFilename;
    }

    /**
     * 解析url，获取文件的完整路径
     * @param fileURL 输入文件url
     * @return 返回一个文件本地路径
     */
    public static String URLToFilePath(String fileURL) {
        String basePath = "D:\\rs\\resp";
        String filePath = null;
        try {
            URL url = new URL(fileURL);
            filePath = url.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assert filePath != null;
        String resultPath = filePath.replace("/", "\\");

        return basePath + resultPath;
    }

    /**
     * 生成文件URL，用于存储文件
     * @param folderName 用于存储文件的文件夹名字，只能使用file下的
     * @param file 需要存储的文件
     * @param timestamp 外部生成的时间戳
     * */
    public static String createURL(String folderName, MultipartFile file, String timestamp) {
        //返回的URL示例 http://localhost:8080/file/userAvatar/1710898888762_099f0f707410eb81dd3133d6c1b73b3c_1027912981914930706.jpg
        return "http://localhost:8080/file/" + folderName + '/' + timestamp + "_" + file.getOriginalFilename();
    }

    /**
     * 删除文件对象（有默认值）
     * @param object 需要删除数据的数据对象
     * @param columnsName 需要删除的数据对象的数据列的数据，注意只要数据库的数据列名即可，只要数据库的数据列名即可，只要数据库的数据列名即可，小写就小写，不用管，我写了个方法类转换为大写
     * @param oldURL 需要删除数据的数据对象中的存储着的旧文件的URL
     * @param defaultFileURL 默认文件，如果存放着的是默认文件不会进行删除
     * @return 返回一个布尔值，true有可能为没有文件存在，文件为默认文件和删除文件成功，false一定为删除文件失败
     * */
    public static boolean removeOldFile(Object object, String columnsName, String oldURL, String defaultFileURL) {
        //如果旧文件为空，则不进行删除
        if (oldURL != null) {
            //如果旧文件的url不等于默认文件的url，则进行删除
            if (!oldURL.equals(defaultFileURL)) {
                //通过上面的URL转为Path函数，获取旧文件的Path
                String oldPath = FileUtils.URLToFilePath(oldURL);
                //创建文件对象，基于就文件的Path
                File oldFile = new File(oldPath);
                if (oldFile.delete()) {
                    //如果删除成功，则返回true,并将传入的数据对象的对应的数据set为空或者默认
                    //得到传入的数据对象的需要删除的数据的set方法名,将闯入的数据列名的第一个字母变为大写
                    String setMethodName = "set" + StringUtils.uppercaseFirst(columnsName);
                    //获取传入对象的全部方法
                    Method[] methods = object.getClass().getMethods();
                    //遍历传入对象的所有方法
                    for(Method method : methods) {
                        if (method.getName().equals(setMethodName)) {
                            try {
                                //进行反射，调用该object中的method方法，传入的值为(object)null即为空
                                method.invoke(object, (Object)null );
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    //删除成功
                    return true;
                }
                else {
                    //删除失败，返回删除失败
                    return false;
                }
            }
            else {
                //默认值，不删除
                return true;
            }
        }
        //文件为空，不删除
        return true;
    }

    /**
     * 删除文件对象（没有默认值）
     * @param object 需要删除数据的数据对象
     * @param columnsName 需要删除的数据对象的数据列的数据，注意只要数据库的数据列名即可，只要数据库的数据列名即可，只要数据库的数据列名即可，小写就小写，不用管，我写了个方法类转换为大写
     * @param oldURL 需要删除数据的数据对象中的存储着的旧文件的URL
     * @return 返回一个布尔值，true有可能为没有文件存在，文件为默认文件和删除文件成功，false一定为删除文件失败
     * */
    public static boolean removeOldFile(Object object, String columnsName, String oldURL) {
        //如果旧文件为空，则不进行删除
        if (oldURL != null) {
            //通过上面的URL转为Path函数，获取旧文件的Path
            String oldPath = FileUtils.URLToFilePath(oldURL);
            //创建文件对象，基于就文件的Path
            File oldFile = new File(oldPath);
            if (oldFile.delete()) {
                //如果删除成功，则返回true,并将传入的数据对象的对应的数据set为空或者默认
                //得到传入的数据对象的需要删除的数据的set方法名,将闯入的数据列名的第一个字母变为大写
                String setMethodName = "set" + StringUtils.uppercaseFirst(columnsName);
                //获取传入对象的全部方法
                Method[] methods = object.getClass().getMethods();
                //遍历传入对象的所有方法
                for(Method method : methods) {
                    if (method.getName().equals(setMethodName)) {
                        try {
                            //进行反射，调用该object中的method方法，传入的值为(object)null即为空
                            method.invoke(object, (Object)null );
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                //删除成功
                return true;
            }
            else {
                //删除失败，返回删除失败
                return false;
            }
        }
        //文件为空，不删除
        return true;
    }


    /***/

    public static String changeImageElementPicture(String oldData,String newImageURL) {

        //将旧数据变为JSON对象
        JsonObject jsonObject = JsonParser.parseString(oldData).getAsJsonObject();

        //获取旧文件URL
        String oldImageUrl = jsonObject.get("backgroundImage").getAsString();

        //如果旧文件URL不为空
        if (!oldImageUrl.equals("")) {
            //如果旧文件不为默认文件
            if (!Objects.equals(oldImageUrl, "http://localhost:8080/file/imageElementImage/defaultbg.png")) {
                //将URL转为Path
                String oldImagePath = FileUtils.URLToFilePath(oldImageUrl);

                //创建新的文件对象
                File oldFile = new File(oldImagePath);

                //删除旧文件
                if (!oldFile.delete()) {
                    System.out.println("删除失败");
                }

            }
        }

        //将新文件URL替换到数据里
        jsonObject.addProperty("backgroundImage", newImageURL);

        //将新数据返回
        return jsonObject.toString();
    }
}
