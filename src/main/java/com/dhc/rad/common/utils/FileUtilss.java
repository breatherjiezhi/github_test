package com.dhc.rad.common.utils;

//import com.crrcdt.zn.common.web.Result;
import com.dhc.rad.common.web.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//import com.dhc.rad.common.web.AppException;

/**
 * 文件上传至tomcat虚拟路径工具类
 *
 * @author
 *
 */
@Service
public class FileUtilss {

    /**
     * 图片上传到tomcat虚拟路径
     *
     * @param file
     *            MultipartFile文件
     *            自定义文件名称 ：故障图片：故障reportId+上传时间的时分秒毫秒
     *            ；物料图片：物料materialId+上传时间的时分秒
     * @return 返回Result结果集 其中resultMap包含图片高度imgHeight、宽度imgWidth、url
     */
    public static Result pictureUpload(MultipartFile file) {
        Result ret = new Result();
        Map<String, String> resultMap = new HashMap<>();
        if (file.getSize() > 0) {
            try {
                BufferedImage image = ImageIO.read(file.getInputStream());
                if (image != null) {// 如果image=null 表示上传的不是图片格式
                    String imgHeight = String.valueOf(image.getHeight());// 获取图片宽度，单位px
                    String imgWidth = String.valueOf(image.getWidth());// 获取图片高度，单位px
                    resultMap.put("imgHeight", imgHeight);
                    resultMap.put("imgWidth", imgWidth);
                } else {
//                    throw new AppException("所传文件不是图片格式");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String path = "";
            Properties props = System.getProperties(); // 获得系统属性集
            // 获取文件后缀名
            String OriginalFileName = file.getOriginalFilename();

                path ="../webapps/picture/" + OriginalFileName;
                String url = "picture/" + OriginalFileName ;
                resultMap.put("url", url);
                System.out.println("======================path:" + path);


            // 通过MultipartFile的方法直接写文件（注意这个时候）
            File newFile = new File(path);
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            try {
                file.transferTo(newFile);
            } catch (Exception e) {
//                throw new AppException(e.getMessage());
            }
        }
        ret.setMessageMap(resultMap);
        return ret;
    }






}
