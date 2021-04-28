package com.dhc.rad.modules.wx.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 10951
 */
public class WeekUtils {
    /**
     * 获取当前日期的下周一到下周日的所有日期集合
     * @return
     */
    public static List<String> getNextWeekDateList(){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 =Calendar.getInstance();

        cal1.setTime(new Date());

        cal2.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal1.get(Calendar.DAY_OF_WEEK);

        if(dayWeek == 1){
            cal1.add(Calendar.DAY_OF_MONTH, 1);
            cal2.add(Calendar.DAY_OF_MONTH, 7);
        } else {
            cal1.add(Calendar.DAY_OF_MONTH, 1-dayWeek+8);
            cal2.add(Calendar.DAY_OF_MONTH, 1-dayWeek+14);
        }
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(cal1.getTime());

        List<String> dateList = new ArrayList();
        //别忘了，把起始日期加上
        dateList.add(new SimpleDateFormat("yyyy-MM-dd").format(cal1.getTime()));
        // 此日期是否在指定日期之后
        while (cal2.getTime().after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = format.format(cStart.getTime());
//            dateList.add(cStart.getTime());
            dateList.add(dateStr);
        }
        //过滤周六 周日
//      dateList = dateList.stream().limit(5).collect(Collectors.toList());
        return dateList;
    }
}
