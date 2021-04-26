package com.dhc.rad.modules.wxOrder.controller;

import com.dhc.rad.common.utils.JedisUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.BaseController;
import com.dhc.rad.modules.pzMenu.service.PzMenuService;
import com.dhc.rad.modules.pzOrder.service.PzOrderService;
import com.dhc.rad.modules.wxOrder.service.WxOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 10951
 */
@Controller
@RequestMapping(value = "${adminPath}/wx/wxOrder")
public class WxOrderController extends BaseController {

    @Autowired
    private PzOrderService pzOrderService;

    @Autowired
    private WxOrderService wxOrderService;


    @RequestMapping(value = "checkStock1", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkStock(@RequestParam("menuId") String menuId, @RequestParam("userId") String userId) {
        Map<String, Object> returnMap = new HashMap<>();
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

        //设置redis中锁的key值
        String lockKey = "lockKey";
        Jedis jedis = JedisUtils.getResource();

        try {
            //向redis中设置锁对象 获取锁
            Long lockValue = jedis.setnx(lockKey, "lockValue");
            jedis.expire(lockKey, 5);
            //获取锁失败
            if (lockValue == 0L) {
                addMessageAjax(returnMap, "0", "获取锁对象失败");
                return returnMap;
            }
            //根据菜单id获取套餐余量
            Integer menuCount = null;
            //判断redis是否存在以JedisUtils.KEY_PREFIX+menuId为key的value值
            Boolean exists = JedisUtils.exists(JedisUtils.KEY_PREFIX + menuId);
            String redisValue = JedisUtils.get(JedisUtils.KEY_PREFIX + menuId);
            //该menuId的套餐余量
            menuCount = wxOrderService.findMenuCount(menuId);
            if (exists != null && !exists) {
                JedisUtils.set(JedisUtils.KEY_PREFIX + menuId, String.valueOf(menuCount), 0);
            }
            //redis中的值和数据库中不一致情况
            if (redisValue != null && Integer.parseInt(redisValue) != menuCount) {
                //将套餐余量存储到redis中
                JedisUtils.set(JedisUtils.KEY_PREFIX + menuId, String.valueOf(menuCount), 0);
            }
            // 从redis中取出套餐余量
            Integer stock = Integer.parseInt(JedisUtils.get(JedisUtils.KEY_PREFIX + menuId));
            //套餐余量大于0
            if (stock > 0) {
                int realStock = stock - 1;
                //将剩余库存realStock更新到数据库中
                Integer updateMenuCount = wxOrderService.updateMenuCount(menuId, realStock);
                JedisUtils.set(JedisUtils.KEY_PREFIX + menuId, String.valueOf(realStock), 0);//JedisUtils.set(key,value)
                //更新失败
                if (updateMenuCount <= 0) {
                    //还原redis中的stock值
                    JedisUtils.set(JedisUtils.KEY_PREFIX + menuId, String.valueOf(stock), 0);
                    addMessageAjax(returnMap, "0", "更新套餐余量失败");
                    return returnMap;
                }
                //将userId和menuId存入到订单表中
                Integer saveOrder = pzOrderService.saveOrder(userId, menuId);
                if (saveOrder < 0) {
                    //还原数据库中的menu_count值
                    wxOrderService.updateMenuCount(menuId, stock);
                    //还原redis中的stock值
                    JedisUtils.set(JedisUtils.KEY_PREFIX + menuId, String.valueOf(stock), 0);
                    addMessageAjax(returnMap, "0", "更新套餐余量失败");
                    return returnMap;
                }
                //获取套餐成功
                addMessageAjax(returnMap, "1", "获取套餐成功，剩余库存：" + realStock + "");
                return returnMap;
            } else {
                //套餐余量不足
                addMessageAjax(returnMap, "0", "库存不足，获取套餐失败");
                return returnMap;
            }
        } finally {
            //解锁
            JedisUtils.del(lockKey);
        }

    }


    @RequestMapping(value = "checkStock", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delStock(@RequestParam("menuId") String menuId, @RequestParam("userId") String userId) {
        Map<String, Object> returnMap = new HashMap<>();
        Integer menuCount = wxOrderService.findMenuCount(menuId);
        String lockKey = UUID.randomUUID().toString() + menuId;
        String lockKey_user = UUID.randomUUID().toString() + userId;

        Long setnx = JedisUtils.setnx(lockKey_user, "");
        if(setnx==0){
            addMessageAjax(returnMap, "0", "获取锁对象失败");
            return returnMap;
        }
        Long setnx1 = JedisUtils.setnx(lockKey, "");
        if(setnx1==0){
            addMessageAjax(returnMap, "0", "获取锁对象失败");
            return returnMap;
        }

        try {
            Integer result = Integer.parseInt(JedisUtils.get(JedisUtils.KEY_PREFIX + menuId));
            //redis中的值和数据库中不一致情况
            if (result != null && !result.equals(menuCount)) {
                //将套餐余量存储到redis中
                JedisUtils.set(JedisUtils.KEY_PREFIX + menuId, String.valueOf(menuCount), 0);
                result = Integer.parseInt(JedisUtils.get(JedisUtils.KEY_PREFIX + menuId));
            }
            if (result > 0) {
                int remainStock = result - 1;
                JedisUtils.set("stock", String.valueOf(remainStock), 0);

                System.out.println("Remain Stock: " + remainStock);

                wxOrderService.updateMenuCount(menuId, remainStock);
                //获取套餐成功
                addMessageAjax(returnMap, "1", "获取套餐成功，剩余库存：" + remainStock + "");
                return returnMap;
            } else {
                System.out.println("Remain Stcock: 0");
                //套餐余量不足
                addMessageAjax(returnMap, "0", "库存不足，获取套餐失败");
                return returnMap;
            }
        } catch (Exception e) {
            addMessageAjax(returnMap, "0", "库存不足，获取套餐失败");
            return returnMap;
        } finally {
            // 加锁记得删除锁
            JedisUtils.del(lockKey);
            JedisUtils.del(lockKey_user);
        }
    }
}
