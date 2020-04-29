/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccStoreAttr;

/**
* @Title. @Description.
* 库房字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccStoreAttrService {

	/**
	 * @Description 
	 * 库房字典属性表<BR> 添加AccStoreAttr
	 * @param AccStoreAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccStoreAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 批量添加AccStoreAttr
	 * @param  AccStoreAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccStoreAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 查询AccStoreAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccStoreAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 查询AccStoreAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccStoreAttr queryAccStoreAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public AccStoreAttr queryHosStoreByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改库房时<BR> 查询StoreByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccStoreAttr queryStoreByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 删除AccStoreAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccStoreAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 批量删除AccStoreAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccStoreAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 更新AccStoreAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccStoreAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 批量更新AccStoreAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccStoreAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 库房字典属性表<BR> 导入AccStoreAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccStoreAttr(Map<String,Object> entityMap)throws DataAccessException;
	
}
