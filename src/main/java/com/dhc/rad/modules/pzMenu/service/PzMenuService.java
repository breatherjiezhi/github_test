package com.dhc.rad.modules.pzMenu.service;

import com.dhc.rad.common.config.Global;
import com.dhc.rad.common.persistence.Page;
import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.modules.pzMenu.dao.PzMenuDao;
import com.dhc.rad.modules.pzMenu.entity.PzMenu;
import com.dhc.rad.modules.pzMenuContent.dao.PzMenuContentDao;
import com.dhc.rad.modules.pzMenuContent.entity.PzMenuContent;
import com.dhc.rad.modules.pzMenuFile.dao.PzMenuFileDao;
import com.dhc.rad.modules.pzMenuFile.entity.PzMenuFile;
import com.dhc.rad.modules.pzMenuFile.service.PzMenuFileService;
import com.dhc.rad.modules.sys.entity.User;
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
public class PzMenuService extends CrudService<PzMenuDao, PzMenu> {

    @Autowired
    private PzMenuDao pzMenuDao;

    @Autowired
    private PzMenuContentDao pzMenuContentDao;


    public PzMenu getByid(String id) {
        PzMenu pzMenu = pzMenuDao.get(id);
        pzMenu.setPzMenuContentList(pzMenuContentDao.findListByMenuId(pzMenu.getId()));
        return pzMenu;
    }


    public Page<PzMenu> searchPage(Page<PzMenu> pzMenuPage, PzMenu pzMenu) {
        pzMenu.setPage(pzMenuPage);
        List<PzMenu> list = pzMenuDao.findList(pzMenu);
        for (PzMenu menu : list) {
            menu.setPzMenuContentString(menuToString(pzMenuContentDao.findListByMenuId(menu.getId())));
        }
        pzMenuPage.setList(list);
        return pzMenuPage;
    }

    /**
     * 无分页查询已通过审核的菜单
     * @param pzMenu
     * @return
     */
    public List<PzMenu> search(PzMenu pzMenu) {
        List<PzMenu> list = pzMenuDao.findList(pzMenu);
        for (PzMenu menu : list) {
            menu.setPzMenuContentString(menuToString(pzMenuContentDao.findListByMenuId(menu.getId())));
        }
        return list;
    }

    @Transactional(readOnly = false)
    public Integer saveOrUpdate(PzMenu pzMenu, List<PzMenuContent> pzMenuContentList) {
        if (ObjectUtils.isNotEmpty(pzMenu)) {
            String id = pzMenu.getId();
            if (StringUtils.isNotBlank(id)) {

                pzMenu.preUpdate();
                //设置菜单状态为保存并修改
                pzMenu.setExamineInfo("");
                pzMenu.setMenuStatus(Global.MENU_STATUS_SAVEANDUPDATE);

                for (PzMenuContent pzMenuContent : pzMenuContentList) {
                    pzMenuContent.preUpdate();
                    pzMenuContentDao.update(pzMenuContent);
                }
                return pzMenuDao.update(pzMenu);
            } else {
                //新增createBy updateBy createTime updateTime
                pzMenu.preInsert();
                //新增createBy updateBy createTime updateTime
                Integer flag = pzMenuDao.insert(pzMenu);

                for (PzMenuContent pzMenuContent : pzMenuContentList) {
                    pzMenuContent.preInsert();
                    pzMenuContent.setMenuId(pzMenu.getId());
                    pzMenuContentDao.insert(pzMenuContent);
                }
                return flag;
            }
        }
        return 0;
    }


    @Transactional(readOnly = false)
    public Integer deleteByIds(List<String> ids) {

        //返回
        return pzMenuDao.deleteByIds(ids);
    }

    @Transactional(readOnly = false)
    public Integer updateMenuStatus(PzMenu pzMenu) {
        //更新菜单实体updateBy createBy updateTime
        pzMenu.preUpdate();
        return pzMenuDao.update(pzMenu);
    }

    public List<PzMenu> findMenuList(PzMenu pzMenu) {
        List<PzMenu> menuList = pzMenuDao.findList(pzMenu);
        return menuList;
    }

    @Transactional(readOnly = false)
    public Integer submitPzMenu(PzMenu pzMenu) {
        pzMenu.preUpdate();
        //提交：将菜单状态置为未审核状态
        pzMenu.setMenuStatus(Global.MENU_STATUS_SUBMIT);
        return pzMenuDao.update(pzMenu);
    }

    @Transactional(readOnly = false)
    public Page<PzMenu> findListByNoExamine(Page<PzMenu> pzMenuPage, PzMenu pzMenu) {

        pzMenu.setPage(pzMenuPage);
        //查询审核状态为待审核的数据信息
        pzMenu.setMenuStatus(Global.MENU_STATUS_SUBMIT);
        List<PzMenu> list = pzMenuDao.findList(pzMenu);
        for (PzMenu menu : list) {
            menu.setPzMenuContentString(menuToString(pzMenuContentDao.findListByMenuId(menu.getId())));
        }
        return pzMenuPage.setList(list);
    }

//    @Transactional(readOnly = false)
//    public Integer upPzMenu(List<String> idList) {
//
//        return pzMenuDao.upPzMenu(idList);
//    }
//
//    @Transactional(readOnly = false)
//    public Integer downPzMenu(List<String> ids) {
//        return pzMenuDao.downPzMenu(ids);
//    }

    @Transactional(readOnly = false)
    public Integer findMenuCount(String menuId) {
        return pzMenuDao.findMenuCount(menuId);
    }

    @Transactional(readOnly = false)
    public Integer findVersion(String menuId) {
        return pzMenuDao.findVersion(menuId);
    }


    public List<PzMenu> findListByRid(String restaurantId,String eatDate) {
        List<PzMenu> list = pzMenuDao.findListByRid(restaurantId,eatDate);
        for (PzMenu menu : list) {
            menu.setPzMenuContentString(menuToString(pzMenuContentDao.findListByMenuId(menu.getId())));
        }
        return list;
    }


    public String menuToString(List<PzMenuContent> pzMenuContentList) {
        StringBuffer temp = new StringBuffer();
        for (PzMenuContent pzMenuContent : pzMenuContentList) {
            if (StringUtils.isNotBlank(temp)) {
                temp.append("\n").append(pzMenuContent.getEatDate()).append("(").append(pzMenuContent.getEatWeek()).append("):").append(pzMenuContent.getMenuDetail());
            } else {
                temp.append(pzMenuContent.getEatDate()).append("(").append(pzMenuContent.getEatWeek()).append("):").append(pzMenuContent.getMenuDetail());
            }
        }
        return temp.toString();
    }

}
