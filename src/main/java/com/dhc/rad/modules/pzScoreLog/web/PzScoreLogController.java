package com.dhc.rad.modules.pzScoreLog.web;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.pzScoreLog.entity.PzScoreLog;
import com.dhc.rad.modules.pzScoreLog.service.PzScoreLogService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 10951
 */
@Controller
@RequestMapping(value = "${adminPath}/pzScoreLog")
public class PzScoreLogController extends BaseController {

    @Autowired
    private PzScoreLogService pzScoreLogService;

    @ModelAttribute
    public PzScoreLog get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return pzScoreLogService.get(id);
        } else {

            return new PzScoreLog();
        }
    }

    @RequestMapping(value = {"list"})
    public String list(PzScoreLog pzScoreLog, HttpServletRequest request, HttpServletResponse response, Model model) {
        return "modules/pzScoreLog/pzScoreLogList";
    }

    @RequestMapping(value = {"searchPage"})
    @ResponseBody
    public Map<String, Object> searchPage(PzScoreLog pzScoreLog, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> returnMap = new HashMap<>();
        Page<PzScoreLog> page = null;
        //权限校验
        //系统管理员可以看到所有用户积分记录
        if (UserUtils.getRoleFlag("admins")) {
            page = pzScoreLogService.findPage(new Page<>(request, response), pzScoreLog);
        } else {
            //其他人只能看到自己的积分记录
            String userId = UserUtils.getUser().getId();
            pzScoreLog.setUserId(userId);
            page = pzScoreLogService.findScoreById(new Page<>(request, response), pzScoreLog);
        }


        returnMap.put("total", page.getTotalPage());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("records", page.getCount());
        returnMap.put("rows", page.getList());
        return returnMap;
    }

    @RequestMapping(value = "form")
    public String form(PzScoreLog pzScoreLog, Model model) {
        model.addAttribute("pzScoreLog", pzScoreLog);
        return "modules/pzScoreLog/pzScoreLogForm";
    }

    @RequestMapping(value = {"doSave"})
    @ResponseBody
    public Map<String, Object> doSave(PzScoreLog pzScoreLog, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> returnMap = new HashMap<>();
        Integer flag = 0;

        if (pzScoreLog != null) {
            flag = pzScoreLogService.saveOrUpdate(pzScoreLog);
        }
        if (flag > 0) {
            addMessageAjax(returnMap, "1", "保存成功");
        } else {
            addMessageAjax(returnMap, "0", "保存失败");
        }
        return returnMap;
    }

    @RequestMapping(value = "deleteByIds", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteByIds(String ids, HttpServletRequest request, HttpServletResponse response, Model model) {
        Map<String, Object> returnMap = new HashMap<>();
        Integer flag = pzScoreLogService.deleteByIds(Arrays.asList(ids.split(",")));

        if (flag > 0) {
            addMessageAjax(returnMap, "1", "删除成功");
        } else {
            addMessageAjax(returnMap, "0", "删除失败");
        }

        return returnMap;
    }
}
