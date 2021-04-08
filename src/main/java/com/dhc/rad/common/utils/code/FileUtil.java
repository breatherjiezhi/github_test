/**      
  * @文件名称: FileUtil.java  
  * @类路径: com.dhc.rad.common.utils.code  
  * @描述: TODO  
  * @作者：fangzr   
  * @时间：2015-11-2 下午03:27:03  
  * @版本：V1.0     
  */  
package com.dhc.rad.common.utils.code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @类功能说明： 
 * @类修改者： 
 * @修改日期： 
 * @修改说明：   
 * @公司名称：DHC  
 * @作者：fangzr   
 * @创建时间：2015-11-2 下午03:27:03  
 * @版本：V1.0 
 */
class FileUtil {

    public static void rewrite(File file, String data) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {        
            if(bw != null) {
               try { 
                   bw.close();
               } catch(IOException e) {
                   e.printStackTrace();
               }
            }            
        }
    }
    
    public static List<String> readList(File file) {
        BufferedReader br = null;
        List<String> data = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader(file));
            for(String str = null; (str = br.readLine()) != null; ) {
                data.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null) {
               try { 
                   br.close();
               } catch(IOException e) {
                   e.printStackTrace();
               }
            }
        }
        return data;
    }
}
