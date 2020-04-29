/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Sup;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface SupService {

	/**
	 * @Description 添加Sup
	 * @param Sup entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addSup(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addGroupSup(Map<String, Object> entityMap)throws DataAccessException;
	
	public String addSupTz(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addSupType(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addSupNotType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Sup
	 * @param  Sup entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchSup(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Sup分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String querySup(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询SupByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Sup querySupByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Sup
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteSup(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Sup
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchSup(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Sup
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateSup(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateSupTz(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Sup
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchSup(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Sup
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importSup(Map<String,Object> entityMap)throws DataAccessException;


	/**
	 * 查询 供应商编码、供应商名称是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	List<Sup> querySupById(Map<String, Object> mapVo)throws DataAccessException;
	
}
