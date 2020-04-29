
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiTargetMethodPara;
/**
 * 
 * @Description:
 * 9903 指标取值方法参数表
 * @Table:
 * PRM_TARGET_METHOD_PARA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AphiTargetMethodParaService {

	/**
	 * @Description 
	 * 添加9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmTargetMethodPara(Map<String,Object> entityMap)throws DataAccessException;
	public String addPrmTargetMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmTargetMethodPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmTargetMethodPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmTargetMethodPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmTargetMethodPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmTargetMethodPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmTargetMethodPara(Map<String,Object> entityMap) throws DataAccessException;
	
	
	 
	/**
	 * @Description 
	 * 查询对象9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AphiTargetMethodPara queryPrmTargetMethodParaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
}
