/**
* @Copyright: Copyright (c) 2017-9-13 
* @Company: 杭州亦童科技有限公司
*/
package com.chd.hrp.mat.service.cert;
import java.util.*;

import org.springframework.web.multipart.MultipartFile;

public interface MatCertFileService {
	
	/**
	 * @Description 保存
	 * @param Map<String,Object> 
	 * @return String
	 * @throws Exception
	*/
	public String saveMatCertFile(String table_code, String id_col, String id_val, String old_files, String new_files, List<MultipartFile> fileList)throws Exception;

	/**
	 * @Description 删除文件
	 * @param Map<String,Object>
	 * @return String
	 * @throws Exception
	*/
	public String deleteMatCertFile(String table_code, String id_col, List<String> id_list)throws Exception;
	
	/**
	 * @Description 查询集合
	 * @param Map<String,Object>
	 * @return String
	 * @throws Exception
	*/
	public List<Map<String, Object>> queryMatCertFileList(String table_code, String id_col, String id_val) throws Exception;

	/**
	 * @Description 文件上传前准备工作
	 * @param MultipartFile files
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	public Map<String, Object> upLoadReady(Map<String, Object> entityMap, MultipartFile file)throws Exception;

	/**
	 * @Description 删除文件
	 * @param 需包含file键值为(路径/文件名称.文件类型)
	 * @return Boolean
	 * @throws Exception
	*/
	public Boolean filesDelete(List<Map<String, Object>> entityList);
	
	/**
	 * @Description 删除文件
	 * @param 
	 * @return Boolean
	 * @throws Exception
	*/
	public Boolean fileDelete(String filePath, String fileName);
	
	/**
	 * @Description 文件上传
	 * @param 
	 * @return Boolean
	 * @throws Exception
	*/
	public Boolean fileUpLoad(String filePath, String fileName, MultipartFile file);
}
