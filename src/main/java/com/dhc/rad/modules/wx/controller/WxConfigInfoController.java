package com.dhc.rad.modules.wx.controller;


import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.sys.service.ConfigInfoService;
import com.dhc.rad.modules.sys.utils.ConfigInfoUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/wx/configInfo")
public class WxConfigInfoController extends BaseController {


    @Autowired
    private ConfigInfoService configInfoService;

    /*
     * wx帮助与建议
     */
    @RequestMapping(value = "getHelpInfo")
    @ResponseBody
    public Map<String, Object> getHelpInfo(RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = Maps.newHashMap();

        List<Map<String,Object>> dataList= new ArrayList<>();
        Map<String,Object> map = new HashMap<>();

        String helpInfo = ConfigInfoUtils.getConfigVal("helpInfo");
        map.put("helpInfo",helpInfo);
        String rollInfo = ConfigInfoUtils.getConfigVal("rollInfo");
        map.put("rollInfo",rollInfo);
        dataList.add(map);
        returnMap.put("data", dataList);
        returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
        returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        return returnMap;
    }


}
