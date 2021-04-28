package com.dhc.rad.modules.holiday.service;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.holiday.dao.HolidayDao;
import com.dhc.rad.modules.holiday.entity.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 10951
 */
@Service
@Transactional(readOnly = true)
public class HolidayService extends CrudService<HolidayDao, Holiday> {

    @Autowired
    private HolidayDao holidayDao;

    @Transactional(readOnly = false)
    public Integer saveOrUpdate(Holiday holiday) {
        if(ObjectUtils.isNotEmpty(holiday)){
            if(StringUtils.isNotBlank(holiday.getId())){
                holiday.preUpdate();
                return holidayDao.update(holiday);
            }else{
                holiday.preInsert();
                return holidayDao.insert(holiday);
            }
        }
        return 0;
    }


    @Transactional(readOnly = false)
    public Integer deleteByIds(List<String> ids) {

        return holidayDao.deleteByIds(ids);
    }

    @Transactional(readOnly = false)
    public Integer saveOrUpdateBydDate(Holiday holiday) {
        if(holiday!=null){
            if(ObjectUtils.isNotEmpty(holiday.getHolidayDate()) && StringUtils.isNotBlank(holiday.getHolidayType())){

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String hDate = df.format(holiday.getHolidayDate());

                Holiday newHoliday = holidayDao.getByDate(hDate);

                holiday.setHolidayType(holiday.getHolidayType().contains("节")?holiday.getHolidayType():holiday.getHolidayType()+"节");
                if (ObjectUtils.isNotEmpty(newHoliday)) {
                    holiday.preUpdate();
                    holiday.setId(newHoliday.getId());
                    return holidayDao.update(holiday);
                }else{
                    holiday.preInsert();
                    return holidayDao.insert(holiday);
                }
            }
        }
        return 0;
    }


    @Transactional(readOnly = false)
    public Holiday getByDate(String holidayDate) {
        return holidayDao.getByDate(holidayDate);
    }
}
