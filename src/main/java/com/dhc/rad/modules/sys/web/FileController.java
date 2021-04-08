package com.dhc.rad.modules.sys.web;

import com.dhc.rad.common.config.Global;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping(value = "${adminPath}/file")

public class FileController implements ServletContextAware{
    //Spring这里是通过实现ServletContextAware接口来注入ServletContext对象
    private ServletContext servletContext;


    @RequestMapping(value = "download")
    public void  fileDownload(String fileName,HttpServletResponse response){
        //获取网站部署路径(通过ServletContext对象)，用于确定下载文件位置，从而实现下载
        String path = servletContext.getRealPath("/");
        //不设置缓存
        response.reset();
        //设置文件ContentType类型，这样设置，会自动判断下载文件类型,才能完整的传递文件数据，进行下面的操作.
        response.setContentType("multipart/form-data");
        //设置文件头：最后一个参数是设置下载文件名
        response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
        ServletOutputStream out;
        //通过文件路径获得File对象
//        File file = new File(path + "WEB-INF/download/" + fileName);
        File file = new File(Global.getConfig("FileVirtualPath") + "file/" + fileName);

        try {
            FileInputStream inputStream = new FileInputStream(file);

            //通过response获取ServletOutputStream对象(out)
             out = response.getOutputStream();
//            int b = 0;
//            byte[] buffer = new byte[1024];
//            while (b != -1){
//                b = inputStream.read(buffer);
//                //写到输出流(out)中
//                out.write(buffer,0,b);
//            }
             
          // 输出资源内容到相应对象
     		byte[] b = new byte[1024];
     		int len;
     		while ((len = inputStream.read(b, 0, 1024)) != -1) {
     			response.getOutputStream().write(b, 0, len);
     		}
             
            inputStream.close();
            out.close();
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
//        return response;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}

