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
import com.chd.hrp.cost.entity.CostFassetTypeArrt;

/**
* @Title. @Description.
* 成本_固定资产分类字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostFassetTypeArrtMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 添加CostFassetTypeArrt
	 * @param CostFassetTypeArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostFassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 批量添加CostFassetTypeArrt
	 * @param  CostFassetTypeArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostFassetTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 查询CostFassetTypeArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostFassetTypeArrt>
	 * @throws DataAccessException
	*/
	public List<CostFassetTypeArrt> queryCostFassetTypeArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 查询CostFassetTypeArrt所有数据
	 * @param  entityMap
	 * @return List<CostFassetTypeArrt>
	 * @throws DataAccessException
	*/
	public List<CostFassetTypeArrt> queryCostFassetTypeArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 查询CostFassetTypeArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostFassetTypeArrt queryCostFassetTypeArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 删除CostFassetTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostFassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 批量删除CostFassetTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostFassetTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 更新CostFassetTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostFassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 批量更新CostFassetTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostFassetTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 2016/10/31 lxj 
	 * @Description 
	 * 成本_固定资产分类字典<BR> 删除当前集团、医院、账套下所有分类
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAllCostFassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 2016/10/31 lxj 
	 * @Description 
	 * 成本_固定资产分类字典<BR> 删除当前集团、医院、账套下所有分类
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public void addBatchCostMateTypeArrtNew(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryCostFassetTypeArrtPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
