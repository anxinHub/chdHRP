/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccCusAttr;

/**
* @Title. @Description.
* 客户字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCusAttrService {

	/**
	 * @Description 
	 * 客户字典属性表<BR> 添加AccCusAttr
	 * @param AccCusAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccCusAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 批量添加AccCusAttr
	 * @param  AccCusAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccCusAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 查询AccCusAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCusAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 查询AccCusAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccCusAttr queryAccCusAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 修改客户时<BR> 查询CusByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccCusAttr queryCusByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccCusAttr queryHosCusByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 删除AccCusAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccCusAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 批量删除AccCusAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccCusAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 更新AccCusAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccCusAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 批量更新AccCusAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccCusAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 导入AccCusAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccCusAttr(Map<String,Object> entityMap)throws DataAccessException;
	
}
