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
import com.chd.hrp.cost.entity.CostMateTypeArrt;

/**
* @Title. @Description.
* 成本_材料分类字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostMateTypeArrtMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 添加CostMateTypeArrt
	 * @param CostMateTypeArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostMateTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 批量添加CostMateTypeArrt
	 * @param  CostMateTypeArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostMateTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 查询CostMateTypeArrt分页
	 * @param  entityMap RowBounds
	 * @return List<CostMateTypeArrt>
	 * @throws DataAccessException
	*/
	public List<CostMateTypeArrt> queryCostMateTypeArrt(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 查询CostMateTypeArrt所有数据
	 * @param  entityMap
	 * @return List<CostMateTypeArrt>
	 * @throws DataAccessException
	*/
	public List<CostMateTypeArrt> queryCostMateTypeArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 查询CostMateTypeArrtByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostMateTypeArrt queryCostMateTypeArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 删除CostMateTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostMateTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 批量删除CostMateTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostMateTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 更新CostMateTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostMateTypeArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料分类字典<BR> 批量更新CostMateTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostMateTypeArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 2016/10/31 lxj 
	 * 成本_材料分类字典<BR> 删除所有CostMateTypeArrt
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostMateTypeArrtAll(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description
	 * 2016/10/31 lxj
	 * 成本_材料分类字典<BR> 批量添加CostMateTypeArrt
	 * 数据来源于物流管理系统物资分类
	 * @param  CostMateTypeArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostMateTypeArrtFromMat(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description
	 * 2016/10/31 lxj
	 * 成本_材料分类字典<BR> 批量添加CostMateTypeArrt
	 * 数据来源于物流管理系统物资分类
	 * @param  CostMateTypeArrt entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public void addBatchCostMateTypeArrtNew(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryCostMateTypeArrtPrint(
			Map<String, Object> entityMap) throws DataAccessException;
	
	
}
