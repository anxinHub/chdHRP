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
import com.chd.hrp.cost.entity.CostMateArrt;

/**
* @Title. @Description.
* 成本_材料信息字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostMateArrtMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 添加CostMateArrt
	 * @param CostMateArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostMateArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 批量添加CostMateArrt
	 * @param  CostMateArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostMateArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 查询CostMateArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostMateArrt>
	 * @throws DataAccessException
	*/
	public List<CostMateArrt> queryCostMateArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 查询CostMateArrt所有数据
	 * @param  entityMap
	 * @return List<CostMateArrt>
	 * @throws DataAccessException
	*/
	public List<CostMateArrt> queryCostMateArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 查询CostMateArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostMateArrt queryCostMateArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 删除CostMateArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostMateArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 批量删除CostMateArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostMateArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 更新CostMateArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostMateArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 批量更新CostMateArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostMateArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * 成本材料信息字典<BR>添加CostMateArrt
	 * @param byCodeMap
	 * @return
	 */
	public List<CostMateArrt> queryCostMateArrtAdd(Map<String, Object> byCodeMap) throws DataAccessException;
	
	/**
	 * @Description
	 * 2016/10/31 lxj  
	 * 成本_材料信息字典<BR> 删除所有CostMateArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostMateArrtAll(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description
	 * 2016/10/31 lxj  
	 * 成本_材料信息字典<BR> 删除所有CostMateArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public void addBatchCostMateArrtNew(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryCostMateArrtPrint(
			Map<String, Object> entityMap) throws DataAccessException;
	
}
