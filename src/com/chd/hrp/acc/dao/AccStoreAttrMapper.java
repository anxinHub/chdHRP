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
import com.chd.hrp.acc.entity.AccStoreAttr;

/**
* @Title. @Description.
* 库房字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccStoreAttrMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 添加AccStoreAttr
	 * @param AccStoreAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccStoreAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 批量添加AccStoreAttr
	 * @param  AccStoreAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccStoreAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 查询AccStoreAttr分页
	 * @param  entityMap RowBounds
	 * @return List<AccStoreAttr>
	 * @throws DataAccessException
	*/
	public List<AccStoreAttr> queryAccStoreAttr(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 库房字典属性表<BR> 查询AccStoreAttr所有数据
	 * @param  entityMap
	 * @return List<AccStoreAttr>
	 * @throws DataAccessException
	*/
	public List<AccStoreAttr> queryAccStoreAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 查询AccStoreAttrByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccStoreAttr queryAccStoreAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccStoreAttr queryHosStoreByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 修改库房时<BR> 查询StoreByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccStoreAttr queryStoreByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 删除AccStoreAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccStoreAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 批量删除AccStoreAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccStoreAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 库房字典属性表<BR> 更新AccStoreAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccStoreAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 批量更新AccStoreAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccStoreAttr(List<Map<String, Object>> entityMap)throws DataAccessException;

	//把当前数据修改为历史数据
	public int updateStoreDictHistory(Map<String, Object> entityMap);
}
