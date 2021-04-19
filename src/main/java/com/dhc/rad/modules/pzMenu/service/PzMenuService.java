package com.dhc.rad.modules.pzMenu.service;

import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.pzMenu.dao.PzMenuDao;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzMenu.enums.MenuStatusEnum;
import com.dhc.rad.modules.pzMenuFile.dao.PzMenuFileDao;
import com.dhc.rad.modules.pzMenuFile.entity.PzMenuFile;
import com.dhc.rad.modules.pzMenuFile.service.PzMenuFileService;
import com.dhc.rad.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author 10951
 */
@Service
@Transactional(readOnly = true)
public class PzMenuService extends CrudService<PzMenuDao,PzMenu> {
    
    @Autowired
    private PzMenuDao pzMenuDao;

    @Autowired
    private PzMenuFileDao pzMenuFileDao;

    public  Page<PzMenu> searchPage(Page<PzMenu> pzMenuPage, PzMenu pzMenu) {
        String userId = UserUtils.getUser().getId();
        pzMenu.setPage(pzMenuPage);
        List<PzMenu> list = pzMenuDao.findMenuList(pzMenu,userId);
        pzMenuPage.setList(list);
        return pzMenuPage;
    }

    @Transactional(readOnly = false)
    public Integer saveOrUpdate(PzMenu pzMenu) {
        if(ObjectUtils.isNotEmpty(pzMenu)){
            String id = pzMenu.getId();
            if(StringUtils.isNotBlank(id)){

                pzMenu.preUpdate();
                //设置菜单状态为保存并修改
                pzMenu.setMenuStatus(MenuStatusEnum.MENU_STATUS_SAVEANDUPDATE.getCategory());

                return pzMenuDao.update(pzMenu);
            }else{

                //新增createBy updateBy createTime updateTime
                pzMenu.preInsert();
                //新增createBy updateBy createTime updateTime
                return  pzMenuDao.insert(pzMenu);
            }
        }
        return 0;
    }


    @Transactional(readOnly = false)
    public Integer deleteByIds(List<String> ids) {

        //返回
        return  pzMenuDao.deleteByIds(ids);
    }

    public Integer updateMenuStatus(PzMenu pzMenu) {

        return pzMenuDao.updateMenuStatus(pzMenu);
    }

    public List<PzMenu> findMenuList(PzMenu pzMenu) {
        String userId = UserUtils.getUser().getId();
        List<PzMenu> menuList =  pzMenuDao.findMenuList(pzMenu,userId);
        return menuList;
    }

    public Integer submitPzMenu(PzMenu pzMenu) {
        return pzMenuDao.submitPzMenu(pzMenu);
    }
}
