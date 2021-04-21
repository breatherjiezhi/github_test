package com.dhc.rad.common.utils;

import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.utils.convertUtils.Doc2PdfUtil;
import com.dhc.rad.common.utils.convertUtils.PathUtil;
import com.dhc.rad.common.web.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

//import com.dhc.rad.common.web.AppException;

/**
 * 文件上传至tomcat虚拟路径工具类
 *
 * @author
 *
 */
@Service
public class FileUtil {

    //pzMenuFileUpload
    public static Result pzMenuFileUpload(MultipartFile file, String ctxPath) {
        Result ret = new Result();
        Map<String, String> resultMap = new HashMap<>();
        Date date= new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        String nyr =dateFormat.format(date);

        resultMap.put("error", "no");
        if (file.getSize() > 0) {
            String fileName = file.getOriginalFilename();
            System.out.println("======================fileName:" + fileName);
            String path = "";
            Properties props = System.getProperties(); // 获得系统属性集
            String osName = props.getProperty("os.name"); // 操作系统名称
            // 获取文件后缀名
            // 本地路径
            int dot=fileName.lastIndexOf(".");
            String[] a=new String[2];
            a[0]=fileName.substring(0,dot);
            a[1]=fileName.substring(dot,fileName.length());

            String reallyName= UUID.randomUUID().toString()+a[1];//加时间戳的文件名
            fileName=a[0]+a[1];
            fileName= fileName.replace(" ","");
            System.out.println("======================ctxPath:" + ctxPath);
            String uploadUrl = "pzMenuFile" + File.separator + nyr + File.separator + reallyName;

            ctxPath = ctxPath.substring(0, ctxPath.length() - 5) + uploadUrl;

            resultMap.put("url", uploadUrl);
            resultMap.put("fileName", fileName);
            resultMap.put("reallyName", reallyName);
            System.out.println("======================ctxPath:" + ctxPath);

            System.out.println("======================reallyName:" + reallyName);
            // 通过MultipartFile的方法直接写文件（注意这个时候）
            File newFile = new File(ctxPath);
            if (!newFile.exists()) {
                System.out.println("======================开始了==================");
                newFile.mkdirs();
            }
            try {
                file.transferTo(newFile);
            } catch (Exception e) {
                e.printStackTrace();
                resultMap.put("error", "yes");
            }
        }
        ret.setMessageMap(resultMap);
        return ret;
    }


    /**
     * 图片上传到tomcat虚拟路径
     *
     * @param file
     *            MultipartFile文件
     * @param fileName
     *            自定义文件名称 ：故障图片：故障reportId+上传时间的时分秒毫秒
     *            ；物料图片：物料materialId+上传时间的时分秒
     * @return 返回Result结果集 其中resultMap包含图片高度imgHeight、宽度imgWidth、url
     */
    public Result pictureUpload(MultipartFile file, String fileName) {
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
            System.out.println("======================fileName:" + fileName);
            String path = "";
            Properties props = System.getProperties(); // 获得系统属性集
            String osName = props.getProperty("os.name"); // 操作系统名称
            // 获取文件后缀名
            String OriginalFileName = file.getOriginalFilename();
            String[] split = OriginalFileName.split("\\.");
            if (osName.equals("Windows 7")) {
                // 本地路径
                path = Global.getConfig("FilePathWindows").replace('/', '\\') + "picture\\" + fileName + "."
                        + split[split.length - 1];
                String url = Global.getConfig("FileVirtualPath") + "picture/" + fileName + "."
                        + split[split.length - 1];
                resultMap.put("url", url);
                System.out.println("======================path:" + path);
            } else {
                // linux war路径
                path = Global.getConfig("FilePathLinux") + "picture/" + fileName + "." + split[split.length - 1];
                String url = Global.getConfig("FileVirtualPath") + "picture/" + fileName + "."
                        + split[split.length - 1];
                resultMap.put("url", url);
                System.out.println("======================path:" + path);
            }

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

    /**
     * 上传文件至tomcat虚拟路径
     *
     * @param file   updateBy zhouln
     *            MultipartFile文件
     * @return 返回Result结果集 其中resultMap包含图url
     */
    public Result fileUpload(MultipartFile file) {
        Result ret = new Result();
        Map<String, String> resultMap = new HashMap<>();
        Date date= new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");

            String nyr =dateFormat.format(date);

        resultMap.put("error", "no");
        if (file.getSize() > 0) {
            String fileName = file.getOriginalFilename();
            System.out.println("======================fileName:" + fileName+"----------"+file.getSize()/1024);
            String path = "";
            Properties props = System.getProperties(); // 获得系统属性集
            String osName = props.getProperty("os.name"); // 操作系统名称
            // 获取文件后缀名
            //if (osName.equals("Windows 10")||osName.equals("Windows 7")) {
                // 本地路径
                int dot=fileName.lastIndexOf(".");
                String[] a=new String[2];
                a[0]=fileName.substring(0,dot);
                a[1]=fileName.substring(dot,fileName.length());
//                Date date = new Date();
//                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMddhhmmss");
//                String nimahai=dateFormat.format(date);
//                a[0]+=nimahai;
                fileName=a[0]+nyr+a[1];
                fileName= fileName.replace(" ","");
//                fileName=a[1];
//                path = Global.getConfig("FilePathWindows").replace('/', '\\') + "..\\webapps\\file\\" + fileName;
//                String url = Global.getConfig("FileVirtualPath") + "../webapps/file/" + fileName;
                path = Global.getConfig("FileVirtualPath").replace('/', '\\') + "file\\" + fileName;
                String url = Global.getConfig("FileVirtualPath") + "file/" + fileName;
                resultMap.put("url", url);
                resultMap.put("fileName", fileName);
                resultMap.put("realName", a[0]);
                System.out.println("======================path:" + path);
           /* } else {
                // linux war路径
                path = Global.getConfig("FilePathLinux") + "file/" + fileName;
                String url = Global.getConfig("FileVirtualPath") + "file/" + fileName;
                resultMap.put("url", url);
                resultMap.put("fileName", fileName);
                System.out.println("======================path:" + path);
            }*/

            // 通过MultipartFile的方法直接写文件（注意这个时候）
            File newFile = new File(path);
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            try {
                file.transferTo(newFile);
            } catch (Exception e) {
                resultMap.put("error", "yes");
//                throw new AppException(e.getMessage());
            }
        }
        ret.setMessageMap(resultMap);
        if(resultMap.get("error").equals("no")){
            if(resultMap.get("fileName").indexOf(".doc") != -1 || resultMap.get("fileName").indexOf(".docx") != -1
                    || resultMap.get("fileName").indexOf(".xls") != -1 || resultMap.get("fileName").indexOf(".xlsx") != -1
                    || resultMap.get("fileName").indexOf(".ppt") != -1 || resultMap.get("fileName").indexOf(".pptx") != -1 ){
                new Thread(){
                    String str = "suo";
                    @Override
                    public void run() {
                        synchronized (str){
                            convertTest(resultMap.get("fileName"));
                        }
                    }
                }.start();
            }
        }
        return ret;
    }

    /**
     * 上传表单相关文件至tomcat虚拟路径
     *
     * @param file   updateBy zhouln
     *            MultipartFile文件
     * @return 返回Result结果集 其中resultMap包含图url
     */
    public Result fileFormUpload(MultipartFile file, String serialNumber) {
        Result ret = new Result();
        Map<String, String> resultMap = new HashMap<>();
        Date date= new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("mmssSSS");

        String nyr =dateFormat.format(date);

        resultMap.put("error", "no");
        if (file.getSize() > 0) {
            String fileName = file.getOriginalFilename();
            System.out.println("======================fileName:" + fileName);
            String path = "";
            Properties props = System.getProperties(); // 获得系统属性集
            String osName = props.getProperty("os.name"); // 操作系统名称
            // 获取文件后缀名
            //if (osName.equals("Windows 10")||osName.equals("Windows 7")) {
            // 本地路径
            int dot=fileName.lastIndexOf(".");
            String[] a=new String[2];
            a[0]=fileName.substring(0,dot);
            a[1]=fileName.substring(dot,fileName.length());
//                Date date = new Date();
//                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMddhhmmss");
//                String nimahai=dateFormat.format(date);
//                a[0]+=nimahai;
            fileName=serialNumber+nyr+"_"+a[0]+a[1];
            fileName= fileName.replace(" ","");
//                fileName=a[1];
            path = Global.getConfig("FilePathWindows").replace('/', '\\') + "..\\webapps\\formFile\\" + fileName;
            String url = Global.getConfig("FileVirtualPath") + "../webapps/formFile/" + fileName;
            resultMap.put("url", url);
            resultMap.put("fileName", fileName);
            System.out.println("======================path:" + path);
           /* } else {
                // linux war路径
                path = Global.getConfig("FilePathLinux") + "file/" + fileName;
                String url = Global.getConfig("FileVirtualPath") + "file/" + fileName;
                resultMap.put("url", url);
                resultMap.put("fileName", fileName);
                System.out.println("======================path:" + path);
            }*/

            // 通过MultipartFile的方法直接写文件（注意这个时候）
            File newFile = new File(path);
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            try {
                file.transferTo(newFile);
            } catch (Exception e) {
                resultMap.put("error", "yes");
//                throw new AppException(e.getMessage());
            }
        }
        ret.setMessageMap(resultMap);
        if(resultMap.get("error").equals("no")){
            if(resultMap.get("fileName").indexOf(".doc") != -1 || resultMap.get("fileName").indexOf(".docx") != -1
                    || resultMap.get("fileName").indexOf(".xls") != -1 || resultMap.get("fileName").indexOf(".xlsx") != -1
                    || resultMap.get("fileName").indexOf(".ppt") != -1 || resultMap.get("fileName").indexOf(".pptx") != -1 ){
                new Thread(){
                    String str = "suo";
                    @Override
                    public void run() {
                        synchronized (str){
                            convertForFlow(resultMap.get("fileName"));
                        }
                    }
                }.start();
            }
        }
        return ret;
    }

    //文档转存_流程预览
    public void convertForFlow(String fileName) {
        String path= PathUtil.getWebRootDir()+"../formFile/"+fileName;
        File word = new File(path);
        if(word.exists()){
            //2.装换为pdf文件
            String pdfPath = PathUtil.getWebRootDir()+"../formFile/"+ fileName.substring(0,fileName.lastIndexOf("."))+".pdf";
            //"D:/workspace/bo/WebRoot/pdf/Computer.pdf";
            File pdfFile = new File(pdfPath);
            if(pdfFile.exists())//pdf文件存在则删除
                pdfFile.delete();
            //转换
            Doc2PdfUtil.convert2PDF(word, pdfFile);
        }else{
            System.out.println("要转换的文件不存在");

        }

    }

    //文档在线预览
    public static void convertTest(String fileName) {
        String path= PathUtil.getWebRootDir()+"../file/"+fileName;
        File word = new File(path);
        if(word.exists()){
            //2.装换为pdf文件
            String pdfPath = PathUtil.getWebRootDir()+"../file/"+ fileName.substring(0,fileName.lastIndexOf("."))+".pdf";
            //"D:/workspace/bo/WebRoot/pdf/Computer.pdf";
            File pdfFile = new File(pdfPath);
            if(pdfFile.exists())//pdf文件存在则删除
                pdfFile.delete();
            //转换
            Doc2PdfUtil.convert2PDF(word, pdfFile);
        }else{
            System.out.println("要转换的文件不存在");

        }

    }

    public Result pictureUpload(MultipartFile file) {
        Result ret = new Result();
        Map<String, String> resultMap = new HashMap<>();
        Date date= new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");

        String nyr =dateFormat.format(date);

        resultMap.put("error", "no");
        if (file.getSize() > 0) {
            String fileName = file.getOriginalFilename();
            System.out.println("======================fileName:" + fileName);
            String path = "";
            Properties props = System.getProperties(); // 获得系统属性集
            String osName = props.getProperty("os.name"); // 操作系统名称
            // 获取文件后缀名
            //if (osName.equals("Windows 10")||osName.equals("Windows 7")) {
            // 本地路径
            int dot=fileName.lastIndexOf(".");
            String[] a=new String[2];
            a[0]=fileName.substring(0,dot);
            a[1]=fileName.substring(dot,fileName.length());
            fileName=a[0]+nyr+a[1];
            fileName= fileName.replace(" ","");
            path = Global.getConfig("FilePathWindows").replace('/', '\\') + "..\\webapps\\picture\\" + fileName;
            String url = Global.getConfig("FileVirtualPath") + "../webapps/picture/" + fileName;
            resultMap.put("url", url);
            resultMap.put("fileName", fileName);
            System.out.println("======================path:" + path);

            // 通过MultipartFile的方法直接写文件（注意这个时候）
            File newFile = new File(path);
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            try {
                file.transferTo(newFile);
            } catch (Exception e) {
                resultMap.put("error", "yes");
//                throw new AppException(e.getMessage());
            }
        }
        ret.setMessageMap(resultMap);
        return ret;
    }



}
