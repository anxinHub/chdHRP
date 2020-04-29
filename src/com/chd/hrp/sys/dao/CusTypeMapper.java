/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.sys.entity.CusType;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CusTypeMapper extends SqlMapper{
	
	/**
	 * @Description 添加CusType
	 * @param CusType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCusType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加CusType
	 * @param  CusType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCusType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询CusType分页
	 * @param  entityMap RowBounds
	 * @return List<CusType>
	 * @throws DataAccessException
	*/
	public List<CusType> queryCusType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询CusType所有数据
	 * @param  entityMap
	 * @return List<CusType>
	 * @throws DataAccessException
	*/
	public List<CusType> queryCusType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询CusTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CusType queryCusTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除CusType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCusType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除CusType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCusType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新CusType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCusType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新CusType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCusType(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * 用于集团化管理   集团化客户选择器 ---集团化客户类型
	 * @Description 查询GroupCusType所有数据
	 * @param  entityMap
	 * @return List<CusType>
	 * @throws DataAccessException
	*/
	public List<CusType> queryGroupCusType(Map<String,Object> entityMap) throws DataAccessException;

}
