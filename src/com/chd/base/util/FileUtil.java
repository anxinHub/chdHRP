package com.chd.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import com.chd.base.exception.SysException;


/**
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-7-6 下午7:41:37
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0 
 */
public class FileUtil {
	/** 
	 *  根据路径删除指定的目录或文件，无论存在与否 
	 *@param sPath  要删除的目录或文件 
	 *@return 删除成功返回 true，否则返回 false。 
	 */  
	private static boolean flag=false;
	private static File file=new File("");
	public static boolean deleteFolder(String sPath) {  
	    flag = false;  
	    file = new File(sPath);  
	    // 判断目录或文件是否存在  
	    if (!file.exists()) {  // 不存在返回 false  
	        return flag;  
	    } else {  
	        // 判断是否为文件  
	        if (file.isFile()) {  // 为文件时调用删除文件方法  
	            return deleteFile(sPath);  
	        } else {  // 为目录时调用删除目录方法  
	            return deleteDirectory(sPath);  
	        }  
	    }  
	}
	/** 
	 * 删除单个文件 
	 * @param   sPath    被删除文件的文件名 
	 * @return 单个文件删除成功返回true，否则返回false 
	 */  
	public static boolean deleteFile(String sPath) {  
	    file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) { 
	    	//System.gc(); 
	    	flag = file.delete();  
	    }  
	    return flag;  
	}
	/** 
	 * 删除目录（文件夹）以及目录下的文件 
	 * @param   sPath 被删除目录的文件路径 
	 * @return  目录删除成功返回true，否则返回false 
	 */  
	public static boolean deleteDirectory(String sPath) {  
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
	    if (!sPath.endsWith(File.separator)) {  
	        sPath = sPath + File.separator;  
	    }  
	    File dirFile = new File(sPath);  
	    //如果dir对应的文件不存在，或者不是一个目录，则退出  
	    if (!dirFile.exists() || !dirFile.isDirectory()) {  
	        return false;  
	    }  
	    flag = true;  
	    //删除文件夹下的所有文件(包括子目录)  
	    File[] files = dirFile.listFiles();  
	    for (int i = 0; i < files.length; i++) {  
	        //删除子文件  
	        if (files[i].isFile()) {  
	            flag = deleteFile(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        } //删除子目录  
	        else {  
	            flag = deleteDirectory(files[i].getAbsolutePath());  
	            if (!flag) break;  
	        }  
	    }  
	    if (!flag) return false;  
	    //删除当前目录  
	    if (dirFile.delete()) {  
	        return true;  
	    } else {  
	        return false;  
	    }  
	}  
	
	//目录不存在，创建整个目录，复制文件(PageOffice)
	public static void copyFile(String fromFilePath,String toFilePath) throws IOException {
		
		File file =new File(toFilePath);
		//文件存在
		if(file.exists()){
			return;
		}
		
		//判断base里面模板文件是否存在，不存在取test文件
		file =new File(fromFilePath);
		if(!file.exists()){
			String extStr=fromFilePath.substring(fromFilePath.lastIndexOf("."),fromFilePath.length());
			fromFilePath=fromFilePath.substring(0,fromFilePath.lastIndexOf(File.separator))+File.separator+"test"+extStr;
			
			file =new File(fromFilePath);
			if(!file.exists()) {
				throw new SysException(fromFilePath+"文件不存在！");
			}
		}
		
		//去掉文件名，获取文件夹路径
		file =new File(toFilePath.substring(0,toFilePath.lastIndexOf(File.separator)));
		if(!file.exists()) {
			file.mkdirs();    
		}
		
   		//int bytesum = 0; 
   		int byteread = 0; 
   		File oldfile = new File(fromFilePath);
   		if (oldfile.exists()) { //文件存在时 
   			InputStream inStream = null;
   			FileOutputStream fs = null;
       		try {
				inStream = new FileInputStream(fromFilePath); //读入原文件 
				fs = new FileOutputStream(toFilePath); 
				byte[] buffer = new byte[1444]; 
				while ( (byteread = inStream.read(buffer)) != -1) { 
					//bytesum += byteread; //字节数 文件大小 
					fs.write(buffer, 0, byteread); 
				} 
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(inStream != null) {
					inStream.close();
				}
				if(fs != null) {
					fs.close();
				}
				
			}
   		} 
		
	}
	
	//删除临时文件(PageOffice)
	public static void deleteTempPath(File file) throws Exception {
        if(file.isDirectory()) { 
        	
        	//如果传的是目录，删除取昨天的临时文件
        	Calendar cal=Calendar.getInstance();
        	cal.add(Calendar.DATE, -1);
        	long zuoTime=cal.getTimeInMillis(); 
        	
            File[] filelist = file.listFiles(); 
            for(int i = 0; i < filelist.length; i ++) { 
            	File f=filelist[i];
            	long time=f.lastModified();
            	if(time<zuoTime){
            		f.delete();
            	}
            } 

        }else {
        	
        	//如果传的是文件，直接删除文件
        	if(file.exists()){
        		file.delete();
        	}

        } 
    }

}
