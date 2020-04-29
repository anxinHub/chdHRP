/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service.base;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.EmpDict;
import com.chd.hrp.sys.entity.Mod;
import com.chd.hrp.sys.entity.SysTableStyle;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface SysTableStyleService {
	/** 
	 * @Description 
	 * 查询全部<BR> 
	 * @param  entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	*/
	public String querySysTableStyle(Map<String, Object> entityMap) throws DataAccessException;
	/** 
	 * @Description 
	 * 查询全部<BR> 
	 * @param  entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	*/
	public SysTableStyle querySysTableStyleByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/** 
	 * @Description 
	 * 添加<BR> 
	 * @param  entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	*/
	public String addSysTableStyle(Map<String,Object> entityMap)throws DataAccessException;
   
	/**
	 * @Description 批量添加
	 * @param  EmpDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchSysTableStyle(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteSysTableStyle(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 批量删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchSysTableStyle(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	//保存列表打印格式
	public String saveStyleByUrl(Map<String,Object> map)throws DataAccessException;
  
	//查询列表格式
	public String queryGridByUserID(Map<String,Object> map)throws DataAccessException;
	
	//查询用户打印格式
	public String queryPrintByPageUrl(Map<String,Object> map)throws DataAccessException;
	
	//删除列表格式
	public String deleteStyleByUrl(Map<String,Object> map)throws DataAccessException;

}
