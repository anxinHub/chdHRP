/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）录入科技有限公司
*/
package com.chd.hrp.acc.dao.wagedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWageSchemeItem;

/**
* @Title. @Description.
* 工资方案项目<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageSchemeItemMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 添加AccWageSchemeItem
	 * @param AccWageSchemeItem entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageSchemeItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 批量添加AccWageSchemeItem
	 * @param  AccWageSchemeItem entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageSchemeItem(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 查询AccWageSchemeItem分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageSchemeItem>
	 * @throws DataAccessException
	*/
	public List<AccWageSchemeItem> queryAccWageSchemeItem(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资方案项目<BR> 查询AccWageSchemeItem所有录入
	 * @param  entityMap
	 * @return List<AccWageSchemeItem>
	 * @throws DataAccessException
	*/
	public List<AccWageSchemeItem> queryAccWageSchemeItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 查询AccWageSchemeItemByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageSchemeItem queryAccWageSchemeItemByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 删除AccWageSchemeItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageSchemeItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 批量删除AccWageSchemeItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageSchemeItem(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资方案项目<BR> 更新AccWageSchemeItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageSchemeItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案项目<BR> 批量更新AccWageSchemeItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageSchemeItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryAccWageSchemeItemList(Map<String,Object> entityMap) throws DataAccessException;
	
	//public int initAccWageSchemeItem(List<AccWageSchemeItem> entityMap)throws DataAccessException;
	
}
