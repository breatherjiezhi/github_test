package com.dhc.rad.modules.holiday.web;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.utils.excel.ImportExcel;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.holiday.entity.Holiday;
import com.dhc.rad.modules.holiday.service.HolidayService;
import com.dhc.rad.modules.sys.entity.User;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author 10951
 */
@Controller
@RequestMapping(value = "${adminPath}/holiday")
public class HolidayController extends BaseController {

    @Autowired
    private HolidayService holidayService;

    @ModelAttribute
    public Holiday get(@RequestParam(required = false)String id){
        if(StringUtils.isNotBlank(id)){
            return holidayService.get(id);
        }else {

            return new Holiday();
        }
    }

    @RequestMapping(value = {"list"})
    public String list(Holiday holiday, HttpServletRequest request, HttpServletResponse response, Model model){
        List<Holiday> holidayList = holidayService.findList(holiday);
        model.addAttribute("holidayList", holidayList);
        return "modules/holiday/holidayList";
    }

    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String,Object> searchPage(Holiday holiday,HttpServletRequest request,HttpServletResponse response){
        Page<Holiday> page = holidayService.findHoliday(new Page<>(request, response), holiday);

        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());

        return returnMap;
    }

    @RequestMapping(value = "form")
    public String form(Holiday holiday,Model model){
        model.addAttribute("holiday", holiday);
        return "modules/holiday/holidayForm";
    }

    @RequestMapping(value = {"doSave"})
    @ResponseBody
    public Map<String,Object> doSave(Holiday holiday,HttpServletRequest request,HttpServletResponse response,Model model){
        Map<String,Object> returnMap = new HashMap<>();
        Integer flag = 0;

        if (ObjectUtils.isNotEmpty(holiday)) {
           flag =  holidayService.saveOrUpdate(holiday);
        }
        if (flag > 0) {
            addMessageAjax(returnMap, "1", "保存成功");
        } else {
            addMessageAjax(returnMap, "0", "保存失败");
        }
        return returnMap;
    }

    @RequestMapping(value = "deleteByIds", method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> deleteByIds(String ids, HttpServletRequest request, HttpServletResponse response, Model model){
        Map<String,Object> returnMap = new HashMap<>();
        Integer flag = holidayService.deleteByIds(Arrays.asList(ids.split(",")));

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "删除成功");
        } else {
            addMessageAjax(returnMap, "0", "删除失败");
        }

        return returnMap;
    }

    /**
     * 下载excel模板
     */
    @RequestMapping("downloadexcel")
    public void downloadPermMatrix(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ServletOutputStream out = null;
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("holiday/节假日模板.xlsx");
            String path = url.getPath();
            InputStream fis = new FileInputStream(path);
//            InputStream fis = new FileInputStream(new File("D:\\","节假日模板.xlsx"));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            response.setContentType("application/binary;charset=UTF-8");
            String fileName = java.net.URLEncoder.encode("节假日模版", "UTF-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
            out = response.getOutputStream();
            workbook.write(out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件输出流
        }
    }

    /**
     * 导入数据
     */
    @RequestMapping(value = "importData", method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object>  importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
        Map<String,Object> resultMap = new HashMap<>();
        Integer successResult = 0;
        Integer failResult = 0;
        try {
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<Holiday> holidayList = ei.getDataList(Holiday.class);
            for (Holiday holiday : holidayList) {
                 int resultFlag = holidayService.saveOrUpdateBydDate(holiday);
                 if(resultFlag >0){
                     successResult++;
                 }else {
                     failResult++;
                 }
            }
            if(holidayList.size()==successResult){
                addMessageAjax(resultMap, "1", "全部导入成功");
            }else {
                addMessageAjax(resultMap, "1", successResult + "条数据导入成功，" + "共" + failResult + "条数据导入失败");
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            addMessageAjax(resultMap, "0", "导入失败");
        } catch (Exception e) {
            e.printStackTrace();
            addMessageAjax(resultMap, "0", "导入失败");
        }

        return resultMap;
    }

    @RequestMapping(value ="uploadFileForm", method= RequestMethod.POST)
    public String uploadFileForm(HttpServletRequest request, HttpServletResponse response) {
        return "modules/holiday/uploadFile";
    }


}
