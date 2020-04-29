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
import com.chd.hrp.cost.entity.CostBonusItemArrt;

/**
* @Title. @Description.
* 成本_奖金项属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostBonusItemArrtMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 添加CostBonusItemArrt
	 * @param CostBonusItemArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostBonusItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 批量添加CostBonusItemArrt
	 * @param  CostBonusItemArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostBonusItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 查询CostBonusItemArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostBonusItemArrt>
	 * @throws DataAccessException
	*/
	public List<CostBonusItemArrt> queryCostBonusItemArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 查询CostBonusItemArrt所有数据
	 * @param  entityMap
	 * @return List<CostBonusItemArrt>
	 * @throws DataAccessException
	*/
	public List<CostBonusItemArrt> queryCostBonusItemArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 查询CostBonusItemArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostBonusItemArrt queryCostBonusItemArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 删除CostBonusItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostBonusItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 批量删除CostBonusItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostBonusItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 更新CostBonusItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostBonusItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_奖金项属性表<BR> 批量更新CostBonusItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostBonusItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostBonusItemArrtPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
