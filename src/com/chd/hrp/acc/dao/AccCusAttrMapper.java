/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccCusAttr;

/**
* @Title. @Description.
* 客户字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCusAttrMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 添加AccCusAttr
	 * @param AccCusAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccCusAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 批量添加AccCusAttr
	 * @param  AccCusAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccCusAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 查询AccCusAttr分页
	 * @param  entityMap RowBounds
	 * @return List<AccCusAttr>
	 * @throws DataAccessException
	*/
	public List<AccCusAttr> queryAccCusAttr(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 客户字典属性表<BR> 查询AccCusAttr所有数据
	 * @param  entityMap
	 * @return List<AccCusAttr>
	 * @throws DataAccessException
	*/
	public List<AccCusAttr> queryAccCusAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 查询AccCusAttrByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCusAttr queryAccCusAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccCusAttr queryHosCusByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改客户时<BR> 查询CusByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccCusAttr queryCusByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 删除AccCusAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccCusAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 批量删除AccCusAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccCusAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 客户字典属性表<BR> 更新AccCusAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccCusAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 批量更新AccCusAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccCusAttr(List<Map<String, Object>> entityMap)throws DataAccessException;

	//把当前数据修改为历史数据
	public int updateAccCusAttrHistory(Map<String, Object> entityMap);
	
}
