/**
* @Copyright: Copyright (c) 2017-9-13 
* @Company: 杭州亦童科技有限公司
*/
package com.chd.hrp.mat.serviceImpl.cert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.mat.dao.cert.MatCertFileMapper;
import com.chd.hrp.mat.service.cert.MatCertFileService;

@Service("matCertFileService")
public class MatCertFileServiceImpl implements MatCertFileService {

	private static Logger logger = Logger.getLogger(MatCertFileServiceImpl.class);

	// 引入Service服务
	@Resource(name = "matCertFileMapper")
	private final MatCertFileMapper matCertFileMapper = null;
	
	/**
	 * @Description 保存
	 * @param Map<String,Object>
	 * @return String
	 * @throws Exception
	*/
	@Override
	public String saveMatCertFile(String table_code, String id_col, String id_val, String old_files, String new_files, List<MultipartFile> fileList)
			throws Exception {
		StringBuffer filesPath = new StringBuffer();
		try{
			//处理默认值
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String copy_code = SessionManager.getCopyCode();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", group_id);
			map.put("hos_id", hos_id);
			map.put("copy_code", copy_code);
			map.put("table_code", table_code);  //数据表
			map.put("id_col", id_col);  //ID字段列名
			map.put("id_val", id_val);  //ID值
			map.put("oper_name", SessionManager.getUserName());  //操作人
			map.put("oper_date", new Date());  //操作时间
			map.put("filePath", SessionManager.getRequest().getServletContext().getContextPath()+"/upLoad/mat/"+group_id+hos_id+copy_code+"/cert/"+table_code+"/"+id_val+"/");
			map.put("size_unit", "KB");
			map.put("maxSize", 1024);
			
			old_files = old_files == null ? "" : old_files;
			new_files = new_files == null ? "" : new_files;
			
			List<Map<String, String>> delFileList = new ArrayList<Map<String, String>>();
			if(!"".equals(old_files) && !old_files.equals(new_files)){
				String[] files = old_files.split(",");
				for(int i = 0; i < files.length; i++){
					if(new_files.indexOf(files[i]) == -1){
						Map<String, String> delMap = new HashMap<String, String>();
						String file_path = files[i].substring(0, files[i].lastIndexOf("/")+1);
						String file_name = files[i].substring(files[i].lastIndexOf("/")+1, files[0].length());
						delMap.put("file_path", file_path);
						delMap.put("file_name", file_name);
						delFileList.add(delMap);
					}
				}
			}
			
			//保存新的附件信息和文件
			List<Map<String, Object>> addFileList = new ArrayList<Map<String,Object>>();
			if(fileList != null && fileList.size() > 0){
				for(MultipartFile file : fileList){
					Map<String, Object> fileMap = new HashMap<String, Object>();
					
					//获取上传文件服务端的信息
					fileMap.putAll(upLoadReady(map, file));
	
					fileMap.put("file", file);
					addFileList.add(fileMap);
				}
			}

			//保存文件信息
			if(addFileList.size() > 0){
				matCertFileMapper.addMatCertFileBatch(map, addFileList);
			}
			
			//删除页面替换掉的服务器文件
			if(delFileList.size() > 0){
				//先删除表数据
				matCertFileMapper.deleteMatCertFileByPath(table_code, id_col, id_val, delFileList);
				//删除文件
				for(Map<String, String> delMap : delFileList){
					fileDelete(delMap.get("file_path"), delMap.get("file_name"));
				}
			}
			
			//取出文件路径用于返回
			boolean flag = false;
			List<Map<String, Object>> listPath = matCertFileMapper.queryMatCertFileList(table_code, id_col, id_val);
			if(listPath != null && listPath.size() > 0){
				for(Map<String, Object> pathMap : listPath){
					if(flag){
						filesPath.append(",");
					}else{
						flag = true;
					}
					filesPath.append(pathMap.get("file_path")).append(pathMap.get("file_name"));
				}
			}
			
			//上传文件
			if(addFileList.size() > 0){
				for(Map<String, Object> fileMap : addFileList){
					String file_path = fileMap.get("file_path") == null ? "" : fileMap.get("file_path").toString();
					String file_name = fileMap.get("file_name") == null ? "" : fileMap.get("file_name").toString();
					fileUpLoad(file_path, file_name, (MultipartFile)fileMap.get("file"));
				}
			}
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return filesPath.toString();
	}
	
	/**
	 * @Description 删除
	 * @param Map<String,Object>
	 * @return String
	 * @throws Exception
	*/
	@Override
	public String deleteMatCertFile(String table_code, String id_col, List<String> id_list) throws Exception {
		try{
			//先删除历史附近信息和文件
			List<Map<String, Object>> deleteList = matCertFileMapper.queryMatCertFileDeleteList(table_code, id_col, id_list);
			//删除数据库
			matCertFileMapper.deleteMatCertFile(table_code, id_col, id_list);
			//删除文件
			if(deleteList != null && deleteList.size() > 0){
				filesDelete(deleteList);
			}
		}catch(Exception e){

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return "{\"msg\":\"操作成功\"}";
	}

	/**
	 * @Description 查询集合
	 * @param Map<String,Object>
	 * @return String
	 * @throws Exception
	*/
	@Override
	public List<Map<String, Object>> queryMatCertFileList(String table_code, String id_col, String id_val) throws Exception{
		
		return matCertFileMapper.queryMatCertFileList(table_code, id_col, id_val);
	}

	/**
	 * @Description 文件上传前准备工作
	 * @param Map<String,Object>
	 * @return String
	 * @throws Exception
	*/
	@Override
	public Map<String, Object> upLoadReady(Map<String, Object> entityMap, MultipartFile file) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String locaFileName = "";
		String fileType = "";
		String serverFileName = "";
		String serverFilePath = "";
		String error = "";
		Long fileSize = null;
		
		//是否存在文件对象
		if(file != null){
			//判断文件大小 以KB做为判断
			fileSize=(file.getSize()) / 1024;
			if(entityMap.get("maxSize") != null && !"".equals(entityMap.get("maxSize").toString())){
				if(fileSize > Long.valueOf(entityMap.get("maxSize").toString())){
					error = "{\"wran\":\"上传文件过大，单个文件大小应小于1MB！\"}";
				}
			}
			
			//上传文件的原始名称
			locaFileName = new String(file.getOriginalFilename());  
			//文件类型
			fileType = locaFileName.substring(locaFileName.lastIndexOf("."), locaFileName.length());  
			//使用uuid生成一个新的文件名称
			serverFileName = UUIDLong.absStringUUID() + fileType;  

			serverFilePath = entityMap.get("filePath").toString();
		}
		
		//用于操作sup_file_path这张表的数据
		map.put("file_name", serverFileName);  //服务器文件名
		map.put("file_name_o", locaFileName);  //用户上传的原始文件名
		map.put("file_type", fileType);  //文件类型
		map.put("file_path", serverFilePath);  //服务器路径
		map.put("file_size", fileSize);  //文件大小
		map.put("error", error);  //错误提示
		
		return map;
	}

	/**
	 * @Description 删除文件
	 * @param 需包含file_path、file_name键值为(路径、文件名称.文件类型)
	 * @return Boolean
	 * @throws Exception
	*/
	@Override
	public Boolean filesDelete(List<Map<String, Object>> entityList){
		boolean is_flag = true;
		String filePath = "";
		String fileName = "";
		String locaPath = System.getProperty("hrp.root");  //本地项目根目录
		//处理路径使之符合要求
		String objName = SessionManager.getRequest().getServletContext().getContextPath();
		locaPath = locaPath.replace(objName.replace("/", "\\"), "").replace(objName.replace("\\", "/"), "");
		
		logger.debug("------------准备删除文件------------");
		for(Map<String, Object> map : entityList){
			if(map.get("file_path") != null && !"".equals(map.get("file_path"))){
				filePath = map.get("file_path").toString();
				fileName = map.get("file_name").toString();
				File delFile = new File(locaPath + filePath + fileName);
				if(!delFile.exists()){
					logger.debug("删除时文件不存在："+locaPath + filePath + fileName);
				}else{
					if(delFile.isFile()){
						is_flag = delFile.delete();
						logger.debug("文件删除成功："+locaPath + filePath + fileName);
					}else{
						logger.debug("删除时文件不存在："+locaPath + filePath + fileName);
					}
				}
			}else{
				logger.debug("没有传入file_path参数！");
			}
			if(is_flag == false){
				break;
			}
		}
		logger.debug("------------结束删除文件------------");
		
		return is_flag;
	}
	
	/**
	 * @Description 删除文件
	 * @param 
	 * @return Boolean
	 * @throws Exception
	*/
	@Override
	public Boolean fileDelete(String filePath, String fileName){
		boolean is_flag = true;
		String locaPath = System.getProperty("hrp.root");  //本地项目根目录
		//处理路径使之符合要求
		String objName = SessionManager.getRequest().getServletContext().getContextPath();
		locaPath = locaPath.replace(objName.replace("/", "\\"), "").replace(objName.replace("\\", "/"), "");

		logger.debug("------------准备删除文件------------");
		File delFile = new File(locaPath + filePath + fileName);
		if(!delFile.exists()){
			logger.debug("删除时文件不存在："+locaPath + filePath + fileName);
		}else{
			if(delFile.isFile()){
				is_flag = delFile.delete();
				logger.debug("文件删除成功："+locaPath + filePath + fileName);
			}else{
				logger.debug("删除时文件不存在："+locaPath + filePath + fileName);
			}
		}
		logger.debug("------------结束删除文件------------");
			
		return is_flag;
	}

	/**
	 * @Description 文件上传
	 * @param 
	 * @return Boolean
	 * @throws Exception
	*/
	@Override
	public Boolean fileUpLoad(String filePath, String fileName, MultipartFile mFile){
		String locaPath = System.getProperty("hrp.root");  //本地项目根目录
		//处理路径使之符合要求
		String objName = SessionManager.getRequest().getServletContext().getContextPath();
		locaPath = locaPath.replace(objName.replace("/", "\\"), "").replace(objName.replace("\\", "/"), "");

		boolean is_flag = false;
		try {
			logger.debug("------------准备上传文件------------");
			//上传文件
			File file = new File(locaPath + filePath + fileName);
			if(!file.exists()){
				file.mkdirs();  //如果文件目录不存在则创建
				logger.debug("创建文件夹："+locaPath + filePath);
			}
			mFile.transferTo(file);
			logger.debug("文件上传成功："+locaPath + filePath + fileName);
			is_flag = true;
		} catch (IllegalStateException e) {
			is_flag = false;
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			is_flag = false;
			logger.error(e.getMessage(), e);
		} catch(Exception e) {
			is_flag = false;
			logger.error(e.getMessage(), e);
		}
		logger.debug("------------结束上传文件------------");
		
		return is_flag;
	}
}
