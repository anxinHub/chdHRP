
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.prm.entity.PrmComType;
/**
 * 
 * @Description:
 * 9913 绩效部件类型表
 * @Table:
 * PRM_COM_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmComTypeService {

	/**
	 * @Description 
	 * 添加9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmComType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmComType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmComType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmComType(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmComType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmComType(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmComType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象9913 绩效部件类型表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmComType queryPrmComTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
}
