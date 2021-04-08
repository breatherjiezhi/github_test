/**      
  * @文件名称: FileEveryDaySerialNumber.java  
  * @类路径: com.dhc.rad.common.utils.code  
  * @描述: TODO  
  * @作者：fangzr   
  * @时间：2015-11-2 下午03:30:18  
  * @版本：V1.0     
  */  
package com.dhc.rad.common.utils.code;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @类功能说明： 
 * @类修改者： 
 * @修改日期： 
 * @修改说明：   
 * @公司名称：DHC  
 * @作者：fangzr   
 * @创建时间：2015-11-2 下午03:30:18  
 * @版本：V1.0 
 */
public class FileEveryDaySerialNumber extends EveryDaySerialNumber {

	  /**
     * 持久化存储的文件
     */    
    private File file = null;
    
    /**
     * 存储的分隔符
     */
    private final static String FIELD_SEPARATOR = ",";   

    public FileEveryDaySerialNumber(int width, String path,String filename) {
        super(width);
        file = new File(path,filename);
    }

    @Override
    protected int getOrUpdateNumber(Date current, int start) {
        String date = format(current);
        int num = start;
        if(file.exists()) {
            List<String> list = FileUtil.readList(file);        
            String[] data = list.get(0).split(FIELD_SEPARATOR);
            if(date.equals(data[0])) {
                num = Integer.parseInt(data[1]);
            }
        }
        FileUtil.rewrite(file, date + FIELD_SEPARATOR + (num + 1));
        return num;
    }        

}
