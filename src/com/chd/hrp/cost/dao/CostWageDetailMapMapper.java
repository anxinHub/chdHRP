/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.cost.entity.CostWageDetailMap;

/**
* @Title. @Description.
* 工资明细数据与工资项关系表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostWageDetailMapMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 添加CostWageDetailMap
	 * @param CostWageDetailMap entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostWageDetailMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 批量添加CostWageDetailMap
	 * @param  CostWageDetailMap entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostWageDetailMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 查询CostWageDetailMap分页
	 * @param  entityMap RowBounds
	 * @return List<CostWageDetailMap>
	 * @throws DataAccessException
	*/
	public List<CostWageDetailMap> queryCostWageDetailMap(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 查询CostWageDetailMap所有数据
	 * @param  entityMap
	 * @return List<CostWageDetailMap>
	 * @throws DataAccessException
	*/
	public List<CostWageDetailMap> queryCostWageDetailMap(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 查询CostWageDetailMapByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostWageDetailMap queryCostWageDetailMapByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 删除CostWageDetailMap
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostWageDetailMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 批量删除CostWageDetailMap
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostWageDetailMap(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 更新CostWageDetailMap
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostWageDetailMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资明细数据与工资项关系表<BR> 批量更新CostWageDetailMap
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostWageDetailMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public CostWageDetailMap querySequence()throws DataAccessException;
}
