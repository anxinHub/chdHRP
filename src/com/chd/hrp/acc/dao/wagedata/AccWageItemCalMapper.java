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
import com.chd.hrp.acc.entity.AccWageItemCal;

/**
* @Title. @Description.
* 工资项目计算公式<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageItemCalMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 添加AccWageItemCal
	 * @param AccWageItemCal entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageItemCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 批量添加AccWageItemCal
	 * @param  AccWageItemCal entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageItemCal(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 查询AccWageItemCal分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageItemCal>
	 * @throws DataAccessException
	*/
	public List<AccWageItemCal> queryAccWageItemCal(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资项目计算公式<BR> 查询AccWageItemCal所有数据
	 * @param  entityMap
	 * @return List<AccWageItemCal>
	 * @throws DataAccessException
	*/
	public List<AccWageItemCal> queryAccWageItemCal(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 查询AccWageItemCalByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWageItemCal queryAccWageItemCalByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 删除AccWageItemCal
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageItemCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 批量删除AccWageItemCal
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageItemCal(List<Map<String,Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 更新AccWageItemCal
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageItemCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 批量更新AccWageItemCal
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageItemCal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<AccWageItemCal> queryAccWageItemCalList(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<AccWageItemCal> queryAccWageItemCalList(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryAccWageItemCalExtend(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccWageItemCal> queryAccWageItemCalByCollect(Map<String,Object> entityMap) throws DataAccessException;

}
