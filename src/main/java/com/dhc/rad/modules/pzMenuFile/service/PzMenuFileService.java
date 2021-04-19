package com.dhc.rad.modules.pzMenuFile.service;

import com.dhc.rad.common.service.CrudService;
import com.dhc.rad.common.utils.FileUtil;
import com.dhc.rad.common.utils.ObjectUtils;
import com.dhc.rad.common.utils.StringUtils;
import com.dhc.rad.common.web.Result;
import com.dhc.rad.modules.pzMenuFile.dao.PzMenuFileDao;
import com.dhc.rad.modules.pzMenuFile.entity.PzMenuFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 10951
 */
@Service
@Transactional(readOnly = true)
public class PzMenuFileService extends CrudService<PzMenuFileDao, PzMenuFile> {

    @Autowired
    private PzMenuFileDao pzMenuFileDao;

    public Integer saveOrUpdate(PzMenuFile pzMenuFile) {
        if(pzMenuFile!=null){
            if(StringUtils.isNotBlank(pzMenuFile.getId())){
                pzMenuFile.preUpdate();
                return pzMenuFileDao.update(pzMenuFile);
            }else{
                pzMenuFile.preInsert();
                return pzMenuFileDao.insert(pzMenuFile);
            }
        }
        return 0;
    }

    @Transactional(readOnly = false)
    public Integer deleteByIds(List<String> ids) {

        return pzMenuFileDao.deleteByIds(ids);
    }

    @Transactional(readOnly = false)
    public List<PzMenuFile> checkFileName(String fileName){
        List<PzMenuFile> pzMenuFileList =  pzMenuFileDao.checkFileName(fileName);
        return pzMenuFileList;
    }

    @Transactional(readOnly = false)
    public PzMenuFile pzMenuFileUpload(HttpServletRequest request){
        String ctxPath = request.getSession().getServletContext().getRealPath("");

        MultipartHttpServletRequest multipartRequest =
                WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile file1 = multipartRequest.getFile("impExcelData");
        PzMenuFile pzMenuFile =  pzMenFileUpload1(file1,ctxPath);
        return pzMenuFile;
    }

    private PzMenuFile pzMenFileUpload1(MultipartFile file, String ctxPath) {

        Result fileResult = FileUtil.pzMenuFileUpload(file,ctxPath);
        Map<String, String> fileResultMap = fileResult.getMessageMap();
        PzMenuFile fileEntity = new PzMenuFile();
        fileEntity.setFileName(fileResultMap.get("fileName"));
        fileEntity.setFilePath(fileResultMap.get("url"));
        Integer i=pzMenuFileDao.checkFileName(fileEntity.getFileName()).size();
        if( i==0){
            fileEntity.setVersion("1");
        }else{
            int parseInt = Integer.parseInt(fileEntity.getVersion());
            parseInt++;
            fileEntity.setVersion(String.valueOf(parseInt));
        }
        return fileEntity;
    }

}
