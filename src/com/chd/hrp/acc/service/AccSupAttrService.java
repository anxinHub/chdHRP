/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccSupAttr;

/**
* @Title. @Description.
* 供应商字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccSupAttrService {

	/**
	 * @Description 
	 * 供应商字典属性表<BR> 添加AccSupAttr
	 * @param AccSupAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 批量添加AccSupAttr
	 * @param  AccSupAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccSupAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 查询AccSupAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccSupAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 查询AccSupAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccSupAttr queryAccSupAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public AccSupAttr queryHosSupByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 删除AccSupAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 批量删除AccSupAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccSupAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 更新AccSupAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 批量更新AccSupAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccSupAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 导入AccSupAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccSupAttr(Map<String,Object> entityMap)throws DataAccessException;
	
}
