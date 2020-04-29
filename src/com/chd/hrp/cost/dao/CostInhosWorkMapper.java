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
import com.chd.hrp.cost.entity.CostInhosWork;

/**
* @Title. @Description.
* 住院工作量表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostInhosWorkMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 添加CostInhosWork
	 * @param CostInhosWork entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostInhosWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 批量添加CostInhosWork
	 * @param  CostInhosWork entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostInhosWork(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 查询CostInhosWork分页
	 * @param  entityMap RowBounds
	 * @return List<CostInhosWork>
	 * @throws DataAccessException
	*/
	public List<CostInhosWork> queryCostInhosWork(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 住院工作量表<BR> 查询CostInhosWork所有数据
	 * @param  entityMap
	 * @return List<CostInhosWork>
	 * @throws DataAccessException
	*/
	public List<CostInhosWork> queryCostInhosWork(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 查询CostInhosWorkByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostInhosWork queryCostInhosWorkByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 删除CostInhosWork
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostInhosWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 批量删除CostInhosWork
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostInhosWork(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 住院工作量表<BR>按天刪除
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMonthlyCostInhosWork(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 住院工作量表<BR> 更新CostInhosWork
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostInhosWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 住院工作量表<BR> 批量更新CostInhosWork
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostInhosWork(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostInhosWorkPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
