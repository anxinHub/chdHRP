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
import com.chd.hrp.cost.entity.CostWageItemArrt;

/**
* @Title. @Description.
* 成本_工资项属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostWageItemArrtMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 添加CostWageItemArrt
	 * @param CostWageItemArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostWageItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 批量添加CostWageItemArrt
	 * @param  CostWageItemArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostWageItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 查询CostWageItemArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostWageItemArrt>
	 * @throws DataAccessException
	*/
	public List<CostWageItemArrt> queryCostWageItemArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 查询CostWageItemArrt所有数据
	 * @param  entityMap
	 * @return List<CostWageItemArrt>
	 * @throws DataAccessException
	*/
	public List<CostWageItemArrt> queryCostWageItemArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 查询CostWageItemArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostWageItemArrt queryCostWageItemArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 删除CostWageItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostWageItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 批量删除CostWageItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostWageItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 更新CostWageItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostWageItemArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 批量更新CostWageItemArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostWageItemArrt(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 批量添加CostWageItemArrt
	 * @param  CostWageItemArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public void addBatchCostWageItemArrtNew(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryCostWageItemArrtPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
