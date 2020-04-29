/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccWageScheme;

/**
* @Title. @Description.
* 工资方案<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageSchemeService {

	/**
	 * @Description 
	 * 工资方案<BR> 添加AccWageScheme
	 * @param AccWageScheme entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案<BR> 批量添加AccWageScheme
	 * @param  AccWageScheme entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案<BR> 查询AccWageScheme分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案<BR> 查询AccWageSchemeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageScheme queryAccWageSchemeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案<BR> 删除AccWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案<BR> 批量删除AccWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案<BR> 更新AccWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案<BR> 批量更新AccWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资方案<BR> 导入AccWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 工资方案<BR> 查询AccWageScheme菜单
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageSchemeByMenu(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String queryAccWageSchemeByEmpList(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccWageSchemeByWageList(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccWageBySchemeList(Map<String,Object> entityMap) throws DataAccessException;
	
	public String setAccWageSchemeRela(Map<String,Object> entityMap)throws DataAccessException;

	public String setAccWageItemSumSchemeRela(Map<String, Object> entityMap)throws DataAccessException;
	
	public String addBatchAccWageSchemeKind(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchAccWageSchemeItem(Map<String,Object> entityMap)throws DataAccessException;
	
	public String initWageSchemeItemByWage(Map<String,Object> entityMap)throws DataAccessException;
	
	public String initWageSchemeKindByWage(Map<String,Object> entityMap)throws DataAccessException;

}
