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
import com.chd.hrp.cost.entity.CostFassetArrt;

/**
* @Title. @Description.
* 成本_固定资产字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostFassetArrtMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 添加CostFassetArrt
	 * @param CostFassetArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostFassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 批量添加CostFassetArrt
	 * @param  CostFassetArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostFassetArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 查询CostFassetArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostFassetArrt>
	 * @throws DataAccessException
	*/
	public List<CostFassetArrt> queryCostFassetArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 查询CostFassetArrt所有数据
	 * @param  entityMap
	 * @return List<CostFassetArrt>
	 * @throws DataAccessException
	*/
	public List<CostFassetArrt> queryCostFassetArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 查询CostFassetArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostFassetArrt queryCostFassetArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 删除CostFassetArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostFassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 批量删除CostFassetArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostFassetArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 更新CostFassetArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostFassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 批量更新CostFassetArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostFassetArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 2016/10/31 lxj 
	 * @Description 
	 * 成本_固定资产字典<BR> 删除删除当前集团、医院、账套下 全部CostFassetArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAllCostFassetArrt(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 2016/10/31 lxj 
	 * @Description 
	 * 成本_固定资产字典<BR> 删除删除当前集团、医院、账套下 全部CostFassetArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public void addBatchCostFassetArrtNew(Map<String, Object> mapVo)throws DataAccessException;

	public List<Map<String, Object>> queryCostFassetArrtPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
