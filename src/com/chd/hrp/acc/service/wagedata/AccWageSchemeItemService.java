/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccWageSchemeItem;

/**
* @Title. @Description.
* 工资方案项目<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageSchemeItemService {

	/**
	 * @Description 
	 * 工资方案项目<BR> 添加AccWageSchemeItem
	 * @param AccWageSchemeItem entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageSchemeItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 批量添加AccWageSchemeItem
	 * @param  AccWageSchemeItem entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageSchemeItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 查询AccWageSchemeItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageSchemeItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 查询AccWageSchemeItemByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageSchemeItem queryAccWageSchemeItemByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 删除AccWageSchemeItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageSchemeItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 批量删除AccWageSchemeItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageSchemeItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 更新AccWageSchemeItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageSchemeItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 批量更新AccWageSchemeItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageSchemeItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 导入AccWageSchemeItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWageSchemeItem(Map<String,Object> entityMap)throws DataAccessException;

}
