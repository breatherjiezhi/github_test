package com.dhc.rad.modules.wx.controller;


import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.modules.sys.entity.ConfigInfo;
import com.dhc.rad.modules.sys.service.ConfigInfoService;
import com.dhc.rad.modules.sys.utils.ConfigInfoUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/wx/wxConfigInfo")
public class WxConfigInfoController {


    @Autowired
    private ConfigInfoService configInfoService;

    /*
     * wx帮助与建议
     */
    @RequestMapping(value = "getConfigInfo")
    @ResponseBody
    public Map<String, Object> getConfigInfo(RedirectAttributes redirectAttributes) {
        Map<String, Object> returnMap = Maps.newHashMap();
        ConfigInfo c1 = configInfoService.get("helpInfo");
        ConfigInfo c2 = configInfoService.get("rollInfo");
        List<ConfigInfo> dataList = new ArrayList<ConfigInfo>();
        dataList.add(c1);
        dataList.add(c2);
        returnMap.put("data", dataList);
        returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
        returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        return returnMap;
    }


}
