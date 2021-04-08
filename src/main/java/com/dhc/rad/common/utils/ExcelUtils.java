package com.dhc.rad.common.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;
 
/**
 * @desc: excel工具
 * @author: hj
 * @createTime: 2016-10-10 上午11:32:17
 * @version: v1.0
 */
public class ExcelUtils {
     
    /**
    * excel导出工具
    * @author: bingye
    * @createTime: 2016-10-10 上午11:32:17
    * @param <E>
    * @param response
    * @param header
    * @param title
    * @param fileNames
    * @param list void
    * @throws UnsupportedEncodingException 
    */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <E> void exportExcel(HttpServletResponse response,String title,String[] header,String[] fileNames,List<E> list) throws UnsupportedEncodingException {
    	//创建工作簿
        HSSFWorkbook wb=new HSSFWorkbook();
        //创建一个sheet
        HSSFSheet sheet=wb.createSheet(title);
        
        HSSFRow headerRow=sheet.createRow(0);
        HSSFRow contentRow=null;
        
        /*--------*/
        HSSFCellStyle headerStyle = wb.createCellStyle(); //标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = wb.createFont();	//标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short)11);
		headerStyle.setFont(headerFont);
		short width = 20,height=25*20;
		sheet.setDefaultColumnWidth(width);
		sheet.getRow(0).setHeight(height);
		
        //设置标题
        for(int i=0;i<header.length;i++){
            headerRow.createCell(i).setCellValue(header[i]);
            headerRow.setRowStyle(headerStyle);
        }
		
        /*--------*/
        HSSFCellStyle contentStyle = wb.createCellStyle(); //内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
        try {
            for(int i=0;i<list.size();i++){
                contentRow=sheet.createRow(i+1);
                //获取每一个对象
                E o=list.get(i);
                Class cls=o.getClass();
                for(int j=0;j<fileNames.length;j++){
                    String fieldName = fileNames[j].substring(0, 1).toUpperCase()+ fileNames[j].substring(1);
                    Method getMethod = cls.getMethod("get" + fieldName);
                    Object value = getMethod.invoke(o);
                    if(value!=null){
                    	
                    	if(value instanceof Date){
            				contentRow.createCell(j).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
            				contentRow.setRowStyle(contentStyle);
                    	}else{
                    		
                    		contentRow.createCell(j).setCellValue(value.toString());
                    		/*--------*/
                    		contentRow.setRowStyle(contentStyle);
                    	}
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
         
        OutputStream os=null;
        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename="+Encodes.urlEncode(title)+".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8"); 
            os=response.getOutputStream();
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void exportExcel2(HttpServletResponse response,String[] title,List<String[]> headerList,List<String[]> fileNamesList,List<List> lists) throws UnsupportedEncodingException {
        //创建工作簿
        HSSFWorkbook wb=new HSSFWorkbook();
        for(int m=0;m<lists.size();m++){
            //创建一个sheet
            HSSFSheet sheet=wb.createSheet(title[m]);


            HSSFRow headerRow=sheet.createRow(0);
            HSSFRow contentRow=null;


            HSSFCellStyle headerStyle = wb.createCellStyle(); //标题样式
            headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            HSSFFont headerFont = wb.createFont();	//标题字体
            headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            headerFont.setFontHeightInPoints((short)11);
            headerStyle.setFont(headerFont);
            short width = 20,height=25*20;
            sheet.setDefaultColumnWidth(width);
            sheet.getRow(0).setHeight(height);


            String[] header=headerList.get(m);
            String[] fileNames=fileNamesList.get(m);
            //设置标题
            for(int i=0;i<header.length;i++){
                headerRow.createCell(i).setCellValue(header[i]);
                headerRow.setRowStyle(headerStyle);
            }

            HSSFCellStyle contentStyle = wb.createCellStyle(); //内容样式
            contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            List list=lists.get(m);
            try {
                for(int i=0;i<list.size();i++){
                    contentRow=sheet.createRow(i+1);
                    //获取每一个对象
                    Object o=list.get(i);
                    Class cls=o.getClass();
                    for(int j=0;j<fileNames.length;j++){
                        String fieldName = fileNames[j].substring(0, 1).toUpperCase()+ fileNames[j].substring(1);
                        Method getMethod = cls.getMethod("get" + fieldName);
                        Object value = getMethod.invoke(o);
                        if(value!=null){

                            if(value instanceof Date){
                                contentRow.createCell(j).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
                                contentRow.setRowStyle(contentStyle);
                            }else{

                                contentRow.createCell(j).setCellValue(value.toString());

                                contentRow.setRowStyle(contentStyle);
                            }
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }


        OutputStream os=null;
        try {
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename="+Encodes.urlEncode(title[0])+".xls");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            os=response.getOutputStream();
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
     
}