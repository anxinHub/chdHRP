/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.commonbuilder;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccCashType;

/**
* @Title. @Description.
* 现金流量类别<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCashTypeService {

	/**
	 * @Description 
	 * 现金流量类别<BR> 添加AccCashType
	 * @param AccCashType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccCashType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 批量添加AccCashType
	 * @param  AccCashType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccCashType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 查询AccCashType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCashType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 查询AccCashTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccCashType queryAccCashTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 删除AccCashType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccCashType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 批量删除AccCashType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccCashType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 更新AccCashType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccCashType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 批量更新AccCashType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccCashType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 现金流量类别<BR> 导入AccCashType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccCashType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询AccCashType菜单
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCashTypeByMenu(Map<String,Object> entityMap)throws DataAccessException;
	
}
