package com.dhc.rad.modules.wx.controller;


import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.sys.entity.ChangeInfo;
import com.dhc.rad.modules.sys.entity.Office;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.service.ChangeInfoService;
import com.dhc.rad.modules.sys.service.OfficeService;
import com.dhc.rad.modules.sys.service.SystemService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "${adminPath}/wx/wxUser")
public class WxUserController extends BaseController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private ChangeInfoService changeInfoService;

    /**
     * 返回用户信息
     *
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "infoData")
    public Map<String, Object> infoData() {

        Map<String, Object> returnMap = new HashMap<>();
        User user =  systemService.getUserId(UserUtils.getUser().getId());


        if(user!=null){
            Map<String,Object> data = new HashMap<>();
            data.put("name",user.getName());
            data.put("no",user.getNo());
            data.put("officeName",user.getOffice().getName());
            data.put("officeId",user.getOffice().getId());
            data.put("userIntegral",user.getUserIntegral());

            //查询是否有待审核数据
            ChangeInfo changeInfo = new ChangeInfo();
            changeInfo.setChangeType(1);
            changeInfo.setApplyStatus(2);
            changeInfo.setCreateBy(user);

            List<ChangeInfo> list = changeInfoService.findList(changeInfo);
            if(list.size()>0){
                data.put("officeFlag",true);
            }else{
                data.put("officeFlag",false);
            }
            returnMap.put("data", data);
            returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
            returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        }else{
            returnMap.put("data", null);
            returnMap.put("status", ConstantUtils.ResCode.NODATA);
            returnMap.put("message", ConstantUtils.ResCode.NODATAMSG);
        }



        return returnMap;
    }





    /**
     * 返回用户信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"findServiceUnit"}, method = RequestMethod.POST)
    public Map<String, Object> findServiceUnit() {
        Map<String, Object> returnMap = new HashMap<>();
        List<Office> list  = officeService.findServiceUnit();
        List<Map<String,Object>> dataList = new ArrayList<>();
         for (Office office : list) {
             Map<String,Object> map = new HashMap<>();
             map.put("id",office.getId());
             map.put("officeName",office.getName());
             map.put("parentId",office.getParentId());
             dataList.add(map);
        }
        returnMap.put("data", dataList);
        returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
        returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        return returnMap;
    }



    /**
     * 变更服务单元
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"changeServiceUnit"}, method = RequestMethod.POST)
    public  Map<String,Object> changeServiceUnit(@RequestParam("newValue") String newValue){
        Map<String, Object> returnMap = new HashMap<>();

        User user =  systemService.getUserId(UserUtils.getUser().getId());

        ChangeInfo changeInfo = new ChangeInfo();
        changeInfo.setChangeType(1);
        changeInfo.setApplyStatus(2);
        changeInfo.setCreateBy(user);
        List<ChangeInfo> list = changeInfoService.findList(changeInfo);
        if(list.size()>0){
            returnMap.put("status", ConstantUtils.ResCode.PARMERROR);
            returnMap.put("message", ConstantUtils.ResCode.FAILMSG);
            return returnMap;
        }

        ChangeInfo changeInfo1 = new ChangeInfo();
        changeInfo1.setChangeType(1);
        changeInfo1.setOldValue(user.getOffice().getId());
        changeInfo1.setNewValue(newValue);
        changeInfo1.setApplyStatus(2);
       Integer flag =  changeInfoService.insert(changeInfo1);
       if(flag==1){
           returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
           returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
       }else{
           returnMap.put("status", ConstantUtils.ResCode.PARMERROR);
           returnMap.put("message", ConstantUtils.ResCode.FAILMSG);
       }
        return returnMap;
    }






}
