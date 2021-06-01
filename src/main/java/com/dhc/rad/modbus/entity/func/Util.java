package com.dhc.rad.modbus.entity.func;

import com.dhc.rad.common.config.Global;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.BitSet;

/**
 *
 * @author ares
 */
public class Util {

    public static String getBinaryString(short byteCount, BitSet coilStatus) {
        StringBuilder bitString = new StringBuilder();
        int bitCount = 0;
        for (int i = byteCount * 8 - 1; i >= 0; i--) {
            boolean state = coilStatus.get(i);
            bitString.append(state ? '1' : '0');

            bitCount++;
            if (bitCount == 8 && i > 0) {
                bitCount = 0;
                bitString.append("#");
            }
        }
        return bitString.toString();
    }


    public static String getImgUrl(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String localAddr = Global.getConfig("projectUrl");
        String imgUrl= "https://" + localAddr + File.separator;
        return  imgUrl;
    }


}
