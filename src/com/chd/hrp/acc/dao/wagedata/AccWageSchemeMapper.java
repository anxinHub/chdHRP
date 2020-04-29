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
import com.chd.hrp.acc.entity.AccWageScheme;

/**
* @Title. @Description.
* 工资录入<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageSchemeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资录入<BR> 添加AccWageScheme
	 * @param AccWageScheme entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资录入<BR> 批量添加AccWageScheme
	 * @param  AccWageScheme entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资录入<BR> 查询AccWageScheme分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageScheme>
	 * @throws DataAccessException
	*/
	public List<AccWageScheme> queryAccWageScheme(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资录入<BR> 查询AccWageScheme所有录入
	 * @param  entityMap
	 * @return List<AccWageScheme>
	 * @throws DataAccessException
	*/
	public List<AccWageScheme> queryAccWageScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资录入<BR> 查询AccWageSchemeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageScheme queryAccWageSchemeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 通过主键查一个工资方案
	 */
	public AccWageScheme queryAccWageSchemeByPK(Map<String,Object> paramMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资录入<BR> 删除AccWageScheme
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资录入<BR> 批量删除AccWageScheme
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资录入<BR> 更新AccWageScheme
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资录入<BR> 批量更新AccWageScheme
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageScheme(List<Map<String, Object>> entityMap)throws DataAccessException;

}
