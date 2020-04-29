package com.chd.base.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-7-7 上午10:55:27
 * @Company: 智慧云康（北京）数据科教有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class UploadUtil {
	//public static List<String[]> list= new ArrayList<String[]>();
	/**
	 * 上传文件到服务器指定地址
	 * 
	 * @param plupload
	 *            上传工具类 获取文件相关信息
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @return
	 */
	public static List<File> upload(Plupload plupload,String pathName, HttpServletRequest request, HttpServletResponse response) {
		List<File> list = new ArrayList<File>();
		// 获取请求
		plupload.setRequest(request);
		try {
			// 上传文件
			list = PluploadUtil.upload(plupload,pathName);
			// 判断文件是否上传成功（被分成块的文件是否全部上传完成）
			if (PluploadUtil.isUploadFinish(plupload)) {
				System.out.println(plupload.getName() + "----");
			}

		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 上传文件到服务器指定地址(人力系统使用)
	 * 
	 * @param plupload
	 *            上传工具类 获取文件相关信息
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @return
	 */
	public static void uploadExt(Plupload plupload,String pathName, HttpServletRequest request, HttpServletResponse response) {
		// 获取请求
		plupload.setRequest(request);
		try {
			// 上传文件
			PluploadUtil.uploadExt(plupload,pathName);
			// 判断文件是否上传成功（被分成块的文件是否全部上传完成）
			if (PluploadUtil.isUploadFinish(plupload)) {
				System.out.println(plupload.getName() + "----");
			}

		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 上传文件到服务器指定地址
	 * 
	 * @param plupload
	 *            上传工具类 获取文件相关信息
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @return
	 */
	public static List<File> uploadMore(Plupload plupload,MultipartFile uploadFile,String pathName, HttpServletRequest request, HttpServletResponse response) {
		List<File> list = new ArrayList<File>();
		// 获取请求
		plupload.setRequest(request);
		try {
			// 上传文件
			list = PluploadUtil.uploadMore(plupload,pathName,uploadFile);
			// 判断文件是否上传成功（被分成块的文件是否全部上传完成）
			if (PluploadUtil.isUploadFinish(plupload)) {
				System.out.println(plupload.getName() + "----");
			}
			
		}
		catch (IllegalStateException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 读取文件获取文件的内容，通过IO流的方式读取
	 * 
	 * @param plupload
	 *            上传工具类 获取文件相关信息 此处主要回去文件名以及文件类型
	 * @param request
	 *            请求
	 * @param response
	 *            响应
	 * @return
	 */
	public static List<String[]> readFile(Plupload plupload, HttpServletRequest request, HttpServletResponse response) {
		// 获取请求
		List<String[]> list= new ArrayList<String[]>();
		plupload.setRequest(request);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) plupload.getRequest();   
        MultiValueMap<String, MultipartFile> map = multipartRequest.getMultiFileMap();  
        //获取文件名
        String filename = plupload.getName();   
        if(map != null) {  
            Iterator<String> iter = map.keySet().iterator();  
            while(iter.hasNext()) {  
                String str = (String) iter.next();  
                List<MultipartFile> fileList =  map.get(str); 
                List<String[]> list2= new ArrayList<String[]>();
                for(MultipartFile multipartFile : fileList) {  
                	try {
                		ExcelReader.readExcelList(multipartFile.getInputStream(),filename,list2);
                    }
                    catch (IOException e) {
	                    e.printStackTrace();
                    }
                	list.addAll(list2);
                }
            }
        }
        return list;
	}

	 
}
