package com.chd.hrp.ass.service.card;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartFile;

public interface AssCardInitBasicService {
	//获取单号
	public String getAssCardNo(Map<String,Object> entityMap)throws DataAccessException;
	
	//导入使用科室
	public String importShareDept(Map<String,Object> entityMap)throws Exception;
	
	public String importFile(Map<String,Object> entityMap,MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response,String filePath)throws Exception;
	
	public String deleteFile(List<Map<String,Object>> entityMap);
	
	public String downloadFile(HttpServletResponse response,Map<String,Object> entityMap);
	
	public String importPhoto(Map<String,Object> entityMap,MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response,String filePath)throws Exception;
	
	public String deletePhoto(List<Map<String,Object>> entityMap);
	
	public String downloadPhoto(HttpServletResponse response,Map<String,Object> entityMap);
	
	public String viewPhoto(HttpServletResponse response,Map<String,Object> entityMap);
	
	public String importAccessory(Map<String,Object> entityMap)throws Exception;

	/**
	 * 导入期初卡片  zhaon
	 * @param entityMap
	 * @return
	 * @throws Exception
	 */
	public String importAssCardInitData(Map<String,Object> entityMap)throws Exception;
	
	
	public String initAccount(Map<String,Object> entityMap)throws Exception;

	/**
	 * 导入期初折旧记录 zhaon
	 * @param entityMap
	 * @return
	 * @throws Exception
	 */
	public String importAssDepreInitData(Map<String, Object> entityMap)throws Exception;

	/**
	 * 导入期初分摊记录  zhaon
	 * @param entityMap
	 * @return
	 * @throws Exception
	 */
	public String importAssManageDepreInitData(Map<String, Object> entityMap)throws Exception;

	/**
	 * 导入期初分期付款 zhaon
	 * @param entityMap
	 * @return
	 * @throws Exception
	 */
	public String importAssPayInitData(Map<String, Object> entityMap)throws Exception;

	/**
	 * 导入期初卡片资产金来源  zhaon
	 * @param entityMap
	 * @return
	 * @throws Exception
	 */
	public String importAssResourceInitData(Map<String, Object> entityMap) throws Exception;
}
