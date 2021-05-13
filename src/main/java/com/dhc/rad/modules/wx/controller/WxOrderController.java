package com.dhc.rad.modules.wx.controller;

import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.utils.ConstantUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.utils.TimeUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modbus.entity.func.Util;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzMenu.service.PzMenuService;
import com.dhc.rad.modules.pzOrder.entity.PzOrder;
import com.dhc.rad.modules.pzOrder.service.PzOrderService;
import com.dhc.rad.modules.pzScoreLog.entity.PzScoreLog;
import com.dhc.rad.modules.pzUserScore.entity.PzUserScore;
import com.dhc.rad.modules.pzUserScore.service.PzUserScoreService;
import com.dhc.rad.modules.sys.entity.User;
import com.dhc.rad.modules.sys.service.SystemService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import com.dhc.rad.modules.wx.utils.RedissonUtils;
import com.dhc.rad.modules.wx.service.WxOrderService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 10951
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/wxOrder")
public class WxOrderController extends BaseController {

    @Autowired
    private WxOrderService wxOrderService;

    @Autowired
    private PzMenuService pzMenuService;

    @Autowired
    private PzOrderService pzOrderService;

    @Autowired
    private SystemService systemService;

    @Autowired
    private PzUserScoreService pzUserScoreService;

    private static final RedissonClient redissonClient = RedissonUtils.getRedissonClient();


    /**
     * @Description: 订餐功能
     * @Param: menuId：套餐id
     * @return: Map<String, Object>
     * @Date: 2021/5/6
     */
    @RequestMapping(value = "orderMenu", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> orderMenu(@RequestParam("menuId") String menuId) {

       /* // 1. 配置文件
        Config config = new Config();
        config.useSingleServer()
                .setAddress(Global.getConfig("redis.address"))
                .setPassword(Global.getConfig("redis.password"))
                .setDatabase(0);
        //2. 构造RedissonClient
        RedissonClient redissonClient = Redisson.create(config);*/

        //3. 设置锁定资源名称
        RLock lock = redissonClient.getLock("redlock");

        Map<String, Object> returnMap = new HashMap<>();
        User user = UserUtils.getUser();
        String userId = user.getId();

        try {
            lock.lock();
            //获取当前时间下一周吃饭时间集合
            List<String> nextWeekDateList = TimeUtils.getNextWeekEatDate();

            if (nextWeekDateList.size() == 0) {
                addMessageAjax(returnMap, "0", "下周均为放假时间，无需订餐");
                return returnMap;
            }

            //时间用逗号","进行拼接
            String nextDate = nextWeekDateList.stream().collect(Collectors.joining(",")) + ",";

            //创建订单对象
            PzOrder order = new PzOrder();
            //吃饭日期 eatDate
            order.setEatDate(nextDate);
            //用户id
            order.setUserId(userId);
            //查询订单信息
            List<PzOrder> pzOrderList = pzOrderService.findList(order);

            if (pzOrderList.size() != 0) {
                addMessageAjax(returnMap, "0", "下周套餐已预定,请返回");
                return returnMap;
            }

            //menuId非空判断
            if (StringUtils.isBlank(menuId)) {
                addMessageAjax(returnMap, "0", "套餐编号不能为空");
                return returnMap;
            }
            //userId非空判断
            if (StringUtils.isBlank(userId)) {
                addMessageAjax(returnMap, "0", "用户编号不能为空");
                return returnMap;
            }

            //判断用户是否有充足的餐券数
            //获取用户当前剩余餐券数
            BigDecimal userIntegral = null;
            if (userId != null) {
                User serviceUser = systemService.getUserId(userId);
                userIntegral = serviceUser.getUserIntegral();
            }

            //计算餐券数
            BigDecimal couponCount = BigDecimal.valueOf(nextWeekDateList.size());
            //剩余餐券数
            BigDecimal remainIntegral = userIntegral.subtract(couponCount);
            //如果不足，返回 重新充值
            int compareTo = remainIntegral.compareTo(BigDecimal.ZERO);
            if (compareTo < 0) {
                addMessageAjax(returnMap, "0", "餐券不足，请充值");
                return returnMap;
            }
            //餐厅套餐信息
            PzMenu pzMenu = pzMenuService.get(menuId);
            if (null == pzMenu) {
                addMessageAjax(returnMap, "0", "查询不到套餐信息，请重新核对");
                return returnMap;
            }

            //查询套餐余量
            Integer menuCount = pzMenu.getMenuCount();

            String menuLimited = pzMenu.getMenuLimited();
            //判断当前套餐是否限量
           if(menuLimited!=null && "1".equals(menuLimited)){
               //剩余套餐数量
               BigDecimal remainMenuDecimal = BigDecimal.valueOf(menuCount).subtract(new BigDecimal(1));
               Integer compare = remainMenuDecimal.compareTo(BigDecimal.ZERO);
               if (compare < 0) {
                   addMessageAjax(returnMap, "0", "套餐余量不足，请选择其他套餐");
                   return returnMap;
               }
           }

            //生成订单
            PzOrder pzOrder = new PzOrder();
            //套餐id
            pzOrder.setMenuId(menuId);
            //用户id
            pzOrder.setUserId(userId);
            //套餐积分
            pzOrder.setMenuIntegral(couponCount);
            //吃饭日期
            pzOrder.setEatDate(nextDate);

            pzOrder.setRestaurantId(pzMenu.getRestaurantId());
            //服务单元id
            String serviceUnitId = UserUtils.getUser().getOffice().getId();
            pzOrder.setServiceUnitId(serviceUnitId);

            //扣除餐券数
            user.setUserIntegral(couponCount);

            //新增记录
            PzScoreLog pzScoreLog = new PzScoreLog();
            //用户id
            pzScoreLog.setUserId(user.getId());
            //餐厅id
            pzScoreLog.setRestaurantId(pzMenu.getRestaurantId());
            //积分类型
            pzScoreLog.setScoreType(Global.SCORE_TYPE_DEDUCT);
            //积分分类
            pzScoreLog.setScoreClassify(Global.SCORE_CLASSIFY_COUPON);
            //积分变动
            pzScoreLog.setScoreChange("-" + pzOrder.getMenuIntegral());
            //变动积分描述
            String description = user.getId() + "-" + user.getLoginName() + "：" + pzScoreLog.getScoreChange() + "积分";
            pzScoreLog.setScoreDescription(description);

            //订餐：新增订单信息 新增用户积分记录信息 更新用户积分(sys_user)
            Integer flag = wxOrderService.orderMenu(pzMenu, pzOrder, user, pzScoreLog);

            if (flag > 0) {
                returnMap.put("consumeIntegral", couponCount.toString());
                addMessageAjax(returnMap, "1", "订餐成功");
            } else {
                addMessageAjax(returnMap, "0", "订餐失败");
            }

            return returnMap;
        } finally {
            lock.unlock();
        }

    }


    /**
     * @Description: 查询当前用户下一周的订餐信息
     * @Param: null
     * @return: Map<String, Object>
     * @Date: 2021/4/29
     */
    @RequestMapping(value = "findOrderNextWeek", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findOrderNextWeekById() {
        Map<String, Object> returnMap = new HashMap<>();
        //获取nextWeekEatDate
        List<String> nextWeekEatDateList = TimeUtils.getNextWeekEatDate();
        String nextWeekEatDate = nextWeekEatDateList.stream().collect(Collectors.joining(",")) + ",";

        String userId = UserUtils.getUser().getId();
        PzOrder order = new PzOrder();
        order.setUserId(userId);
        order.setEatDate(nextWeekEatDate);
        List<PzOrder> list = pzOrderService.findList(order);
        if (list.size() > 0) {
            Map<String, Object> temp = new HashMap<>();
            PzOrder pzOrder = list.get(0);
            temp.put("orderId", pzOrder.getId());
            String eatDate = pzOrder.getEatDate();
            if (eatDate != null) {
                eatDate = eatDate.substring(0, eatDate.length() - 1);
                temp.put("eatDate", eatDate);
            } else {
                temp.put("eatDate", "");
            }
            String noEatDate = pzOrder.getNoEatDate();
            if (noEatDate != null) {
                noEatDate = noEatDate.substring(0, noEatDate.length() - 1);
                temp.put("noEatDate", noEatDate);
            } else {
                temp.put("noEatDate", "");
            }
            PzMenu pzMenu = pzMenuService.get(pzOrder.getMenuId());
            temp.put("menuId", pzMenu.getId());
            temp.put("name", pzMenu.getMenuName());
            temp.put("limited", pzMenu.getMenuLimited());
            temp.put("num", pzMenu.getMenuCount());
            temp.put("menuDescript", pzMenu.getMenuDescription());
            temp.put("imgUrl", Util.getImgUrl() + pzMenu.getMenuImgUrl());
            temp.put("menuType", pzMenu.getMenuType());
            temp.put("price", TimeUtils.getNextWeekEatDate().size());
            returnMap.put("data", temp);
            returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
            returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        } else {
            returnMap.put("data", null);
            returnMap.put("status", ConstantUtils.ResCode.NODATA);
            returnMap.put("message", ConstantUtils.ResCode.NODATAMSG);
        }

        return returnMap;
    }


    /**
     * @Description: 查询当前用户当前周的订餐信息
     * @Param: null
     * @return: Map<String, Object>
     * @Date: 2021/4/29
     */
    @RequestMapping(value = "findOrderCurrentWeek", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findOrderCurrentWeek() {
        Map<String, Object> returnMap = new HashMap<>();
        //获取currentWeekEatDate
        List<String> currentWeekEatDateList = TimeUtils.getCurrentWeekEatDate();
        String currentWeekEatDate = currentWeekEatDateList.stream().collect(Collectors.joining(",")) + ",";

        String userId = UserUtils.getUser().getId();
        PzOrder order = new PzOrder();
        order.setUserId(userId);
        order.setEatDate(currentWeekEatDate);
        List<PzOrder> list = pzOrderService.findList(order);
        if (list.size() > 0) {
            Map<String, Object> temp = new HashMap<>();
            PzOrder pzOrder = list.get(0);
            //订单id
            temp.put("orderId", pzOrder.getId());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate = sdf.format(new Date());
            List<Map<String, Object>> eatDateList = new ArrayList<>();

            String[] eatDateTemp = pzOrder.getEatDate() == null ? new String[0] : pzOrder.getEatDate().split(",");
            String noEatDate = pzOrder.getNoEatDate() == null ? "" : pzOrder.getNoEatDate();
            for (int i = 0; i < eatDateTemp.length; i++) {
                Map<String, Object> tempList = new HashMap<String, Object>();
                if (StringUtils.isNotBlank(eatDateTemp[i])) {
                    tempList.put("eatDate", eatDateTemp[i]);
                    //当前日期是否可吃
                    if (noEatDate.indexOf(eatDateTemp[i]) >= 0) {
                        tempList.put("eatFlag", false);
                    } else {
                        tempList.put("eatFlag", true);
                    }
                    //yes为可选 no为不可选
                    if (nowDate.compareTo(eatDateTemp[i]) <= 0) {
                        tempList.put("checkFlag", false);
                    } else {
                        tempList.put("checkFlag", true);
                    }
                }
                eatDateList.add(tempList);
            }
            temp.put("eatDateList", eatDateList);
            PzMenu pzMenu = pzMenuService.get(pzOrder.getMenuId());
            temp.put("menuId", pzMenu.getId());
            temp.put("name", pzMenu.getMenuName());
            temp.put("limited", pzMenu.getMenuLimited());
            temp.put("num", pzMenu.getMenuCount());
            temp.put("menuDescript", pzMenu.getMenuDescription());
            temp.put("imgUrl", Util.getImgUrl() + pzMenu.getMenuImgUrl());
            temp.put("menuType", pzMenu.getMenuType());
            temp.put("price", TimeUtils.getNextWeekEatDate().size());
            returnMap.put("data", temp);
            returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
            returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
        } else {
            returnMap.put("data", null);
            returnMap.put("status", ConstantUtils.ResCode.NODATA);
            returnMap.put("message", ConstantUtils.ResCode.NODATAMSG);
        }
        return returnMap;
    }


    /**
     * @Description: 吃/不吃
     * @Param: mark:吃/不吃的标志 orderId:订单id date:日期
     * @return: Map<String, Object>
     * @Date: 2021/4/30
     */
    @RequestMapping(value = "chooseEatOrNoEat", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> chooseEatOrNoEat(@RequestParam("mark") String mark, @RequestParam("orderId") String orderId, @RequestParam("date") String date) {
        Map<String, Object> returnMap = new HashMap<>();
        User user = UserUtils.getUser();

        //判断当前时间是否已到截至时间
        String noEatTime = Global.getConfig("pzorder.endDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentTime = simpleDateFormat.format(new Date());
        String endTime = currentTime +" "+noEatTime;
        Date currentDate = new Date();
        Date endDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            endDate = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(currentDate.after(endDate)){
            returnMap.put("data", null);
            returnMap.put("status", ConstantUtils.ResCode.NODATA);
            returnMap.put("message", ConstantUtils.ResCode.ENDDATE);
            return returnMap;
        }

        //判断参数是否为空
        if (mark == null || orderId == null || date == null) {
            returnMap.put("data", null);
            returnMap.put("status", ConstantUtils.ResCode.PARMERROR);
            returnMap.put("message", ConstantUtils.ResCode.ParameterException);
            return returnMap;
        }
        //根据orderId获取订单信息
        PzOrder pzOrder = pzOrderService.get(orderId);
        if (pzOrder == null) {
            returnMap.put("data", null);
            returnMap.put("status", ConstantUtils.ResCode.NODATA);
            returnMap.put("message", ConstantUtils.ResCode.INFONOEXIST);
            return returnMap;
        }
        //判断传入的date是否在eatDate或者noEatDate中
        String eatDate = pzOrder.getEatDate();
        String noEatDate = pzOrder.getNoEatDate();
        //传入的日期不在eatDate中
        if (eatDate != null && !eatDate.contains(date)) {
            returnMap.put("data", null);
            returnMap.put("status", ConstantUtils.ResCode.NODATA);
            returnMap.put("message", ConstantUtils.ResCode.ParameterException);
            return returnMap;
        }

        //选择不吃
        if (mark.equals("false")) {

            //传入的日期在noEatDate中
            if (noEatDate != null) {
                if (noEatDate.contains(date)) {
                    returnMap.put("data", null);
                    returnMap.put("status", ConstantUtils.ResCode.NODATA);
                    returnMap.put("message", ConstantUtils.ResCode.DATEEXIST);
                    return returnMap;
                }
            }
            //将不吃日期设置到noEatDate中
            if (noEatDate == null || noEatDate.equals("")) {
                noEatDate = "" + date + ",";
            } else {
                noEatDate = noEatDate + date + ",";
            }
            pzOrder.setNoEatDate(noEatDate);
            //pz_user_score表中增加个人餐厅积分数
            PzUserScore pzUserScore = null;
            pzUserScore = pzUserScoreService.getByUserIdAndRestaurantId(pzOrder.getUserId(), pzOrder.getRestaurantId());
            //TODO:餐券与积分 1：1
            if (pzUserScore != null) {
                BigDecimal canteenIntegral = pzUserScore.getCanteenIntegral();
                BigDecimal newCanteenIntegral = canteenIntegral.add(new BigDecimal(1));
                pzUserScore.setCanteenIntegral(newCanteenIntegral);
            } else {
                pzUserScore = new PzUserScore();
                pzUserScore.setRestaurantId(pzOrder.getRestaurantId());
                pzUserScore.setUserId(pzOrder.getUserId());
                pzUserScore.setCanteenIntegral(new BigDecimal(1));
            }

            //新增记录数据
            PzScoreLog pzScoreLog = new PzScoreLog();
            //餐厅id
            String restaurantId = pzOrder.getRestaurantId();
            if (restaurantId != null) {
                pzScoreLog.setRestaurantId(restaurantId);
            }
            //用户id
            pzScoreLog.setUserId(pzOrder.getId());
            //积分类型
            pzScoreLog.setScoreType(Global.SCORE_TYPE_CHANGE);
            //积分分类
            pzScoreLog.setScoreClassify(Global.SCORE_CLASSIFY_INTEGRAL);
            //变动积分
            String scoreChange = "+1";
            pzScoreLog.setScoreChange(scoreChange);
            //积分描述
            String description = user.getId() + "-" + user.getLoginName() + "：" + pzScoreLog.getScoreChange() + "积分";
            pzScoreLog.setScoreDescription(description);

            //更新订单信息 新增记录信息 更新个人餐厅积分信息
            Integer flag = wxOrderService.saveOrUpdate(pzOrder, pzScoreLog, pzUserScore);

            if (flag > 0) {
                returnMap.put("data", null);
                returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
                returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
            } else {
                returnMap.put("data", null);
                returnMap.put("status", ConstantUtils.ResCode.SERVERERROR);
                returnMap.put("message", ConstantUtils.ResCode.UPDATEFAIL);
            }

            //选择吃
        } else if ("true".equals(mark)) {
            if (!noEatDate.contains(date)) {
                returnMap.put("data", null);
                returnMap.put("status", ConstantUtils.ResCode.NODATA);
                returnMap.put("message", ConstantUtils.ResCode.INFONOEXIST);
                return returnMap;
            }
            //判断pz_user_score表中个人餐厅积分是否充足
            PzUserScore pzUserScore = pzUserScoreService.getByUserIdAndRestaurantId(pzOrder.getUserId(), pzOrder.getRestaurantId());
            BigDecimal canteenIntegral = pzUserScore.getCanteenIntegral();
            int compareTo = canteenIntegral.compareTo(BigDecimal.ZERO);
            if (compareTo < 0 ) {
                returnMap.put("data", null);
                returnMap.put("status", ConstantUtils.ResCode.SERVERERROR);
                returnMap.put("message", ConstantUtils.ResCode.INTEGRALNOTENOUGH);
            }
            //TODO:餐券积分 1：1
            canteenIntegral = canteenIntegral.subtract(new BigDecimal(1));
            pzUserScore.setCanteenIntegral(canteenIntegral);
            //向pz_score_log记录表添加数据
            PzScoreLog pzScoreLog = new PzScoreLog();
            //用户id
            pzScoreLog.setUserId(pzOrder.getUserId());
            //餐厅id
            pzScoreLog.setRestaurantId(pzOrder.getRestaurantId());
            //积分类型
            pzScoreLog.setScoreType(Global.SCORE_TYPE_DEDUCT);
            //积分分类
            pzScoreLog.setScoreClassify(Global.SCORE_CLASSIFY_INTEGRAL);
            //变动积分
            pzScoreLog.setScoreChange("-1");
            //积分描述
            String description = user.getId() + "-" + user.getLoginName() + "：" + pzScoreLog.getScoreChange() + "积分";
            pzScoreLog.setScoreDescription(description);
            //pz_order表中no_eat_date删除吃的日期
            if (noEatDate != null) {
                if (noEatDate.contains(date)) {
                    String newNoEatDate = noEatDate.replace(date + ",", "");
                    pzOrder.setNoEatDate(newNoEatDate);
                }
            }
            //更新用户积分数据 新增积分记录数据 更新订单数据
            Integer updateData = wxOrderService.updateData(pzUserScore, pzScoreLog, pzOrder);

            if (updateData > 0) {
                returnMap.put("data", null);
                returnMap.put("status", ConstantUtils.ResCode.SUCCESS);
                returnMap.put("message", ConstantUtils.ResCode.SUCCESSMSG);
            } else {
                returnMap.put("data", null);
                returnMap.put("status", ConstantUtils.ResCode.SERVERERROR);
                returnMap.put("message", ConstantUtils.ResCode.UPDATEFAIL);
            }

        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = sdf.format(new Date());
        List<Map<String, Object>> eatDateList = new ArrayList<>();

        String[] eatDateTemp = pzOrder.getEatDate() == null ? new String[0] : pzOrder.getEatDate().split(",");
        noEatDate = noEatDate == null ? "" : pzOrder.getNoEatDate();
        for (int i = 0; i < eatDateTemp.length; i++) {
            Map<String, Object> tempList = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(eatDateTemp[i])) {
                tempList.put("eatDate", eatDateTemp[i]);
                //当前日期是否可吃
                if (noEatDate.indexOf(eatDateTemp[i]) >= 0) {
                    tempList.put("eatFlag", false);
                } else {
                    tempList.put("eatFlag", true);
                }
                //yes为可选 no为不可选
                if (nowDate.compareTo(eatDateTemp[i]) <= 0) {
                    tempList.put("checkFlag", false);
                } else {
                    tempList.put("checkFlag", true);
                }
            }
            eatDateList.add(tempList);
        }
        returnMap.put("eatDateList", eatDateList);

        return returnMap;

    }


}
