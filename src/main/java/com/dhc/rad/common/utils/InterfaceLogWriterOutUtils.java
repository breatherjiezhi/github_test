package com.dhc.rad.common.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InterfaceLogWriterOutUtils {
	
	public static void WriterOutFile(String filePath, String conent) {
		String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		FileWriter fw = null;
		try {
			//如果文件存在，则追加内容；如果文件不存在，则创建文件
			File f=new File(filePath);
			
			//查看文件父目录是否存在，不存在则创建父目录
			File fp=f.getParentFile();
			if(!fp.exists())
			{
				fp.mkdirs();
			}
			
			fw = new FileWriter(f, true);
			} catch (IOException e) {
			     e.printStackTrace();
			}
			PrintWriter pw = new PrintWriter(fw);
			pw.println(today+"\r\n"+conent);
			pw.flush();
			try {
			fw.flush();
			pw.close();
			fw.close();
			} catch (IOException e) {
				e.printStackTrace();
		    }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
