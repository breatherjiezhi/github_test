/**      
  * @文件名称: SerialNumber.java  
  * @类路径: com.dhc.rad.common.utils.code  
  * @描述: TODO  
  * @作者：fangzr   
  * @时间：2015-11-2 下午03:29:33  
  * @版本：V1.0     
  */  
package com.dhc.rad.common.utils.code;

/**
 * @类功能说明： 
 * @类修改者： 
 * @修改日期： 
 * @修改说明：   
 * @公司名称：DHC  
 * @作者：fangzr   
 * @创建时间：2015-11-2 下午03:29:33  
 * @版本：V1.0 
 */
abstract class SerialNumber {
    public synchronized String getSerialNumber() {
        return process();
    }
    protected abstract String process();
}
