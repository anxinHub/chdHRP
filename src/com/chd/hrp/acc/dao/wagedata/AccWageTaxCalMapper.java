/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.wagedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWageTaxCal;

/**
* @Title. @Description.
* 个税计算公式<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageTaxCalMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 添加AccWageTaxCal
	 * @param AccWageTaxCal entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageTaxCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 批量添加AccWageTaxCal
	 * @param  AccWageTaxCal entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageTaxCal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 查询AccWageTaxCal分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageTaxCal>
	 * @throws DataAccessException
	*/
	public List<AccWageTaxCal> queryAccWageTaxCal(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 个税计算公式<BR> 查询AccWageTaxCal所有数据
	 * @param  entityMap
	 * @return List<AccWageTaxCal>
	 * @throws DataAccessException
	*/
	public List<AccWageTaxCal> queryAccWageTaxCal(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 查询AccWageTaxCalByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageTaxCal queryAccWageTaxCalByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 删除AccWageTaxCal
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageTaxCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 批量删除AccWageTaxCal
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageTaxCal(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 个税计算公式<BR> 更新AccWageTaxCal
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageTaxCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 批量更新AccWageTaxCal
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageTaxCal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWageTaxCalLastYear(Map<String,Object> entityMap) throws DataAccessException;
	
}
