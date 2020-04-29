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
import com.chd.hrp.cost.entity.CostBonusDetailMap;
import com.chd.hrp.cost.entity.CostWageDetailMap;

/**
* @Title. @Description.
* 奖金明细数据与工资项关系表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostBonusDetailMapMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 添加CostBonusDetailMap
	 * @param CostBonusDetailMap entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostBonusDetailMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 批量添加CostBonusDetailMap
	 * @param  CostBonusDetailMap entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostBonusDetailMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 查询CostBonusDetailMap分页
	 * @param  entityMap RowBounds
	 * @return List<CostBonusDetailMap>
	 * @throws DataAccessException
	*/
	public List<CostBonusDetailMap> queryCostBonusDetailMap(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 查询CostBonusDetailMap所有数据
	 * @param  entityMap
	 * @return List<CostBonusDetailMap>
	 * @throws DataAccessException
	*/
	public List<CostBonusDetailMap> queryCostBonusDetailMap(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 查询CostBonusDetailMapByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostBonusDetailMap queryCostBonusDetailMapByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 删除CostBonusDetailMap
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostBonusDetailMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 批量删除CostBonusDetailMap
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostBonusDetailMap(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 更新CostBonusDetailMap
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostBonusDetailMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 奖金明细数据与工资项关系表<BR> 批量更新CostBonusDetailMap
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostBonusDetailMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public CostBonusDetailMap querySequenceById()throws DataAccessException;
}
