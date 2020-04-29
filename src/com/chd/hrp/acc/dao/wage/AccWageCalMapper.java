/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.wage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWageCal;

/**
* @Title. @Description.
* 工资套合并计算公式<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageCalMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 添加AccWageCal
	 * @param AccWageCal entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 批量添加AccWageCal
	 * @param  AccWageCal entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageCal(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 查询AccWageCal分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageCal>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccWageCal(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 查询AccWageCal所有数据
	 * @param  entityMap
	 * @return List<AccWageCal>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccWageCal(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 查询AccWageCalByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageCal queryAccWageCalByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 删除AccWageCal
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 批量删除AccWageCal
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageCal(List<Map<String,Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 更新AccWageCal
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 批量更新AccWageCal
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageCal(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<AccWageCal> queryAccWageCalByCal(Map<String,Object> entityMap) throws DataAccessException;
	
}
