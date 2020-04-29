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
import com.chd.hrp.cost.entity.CostClinicWork;

/**
* @Title. @Description.
* 门诊工作量表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostClinicWorkMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 添加CostClinicWork
	 * @param CostClinicWork entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostClinicWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 批量添加CostClinicWork
	 * @param  CostClinicWork entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostClinicWork(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 查询CostClinicWork分页
	 * @param  entityMap RowBounds
	 * @return List<CostClinicWork>
	 * @throws DataAccessException
	*/
	public List<CostClinicWork> queryCostClinicWork(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 门诊工作量表<BR> 查询CostClinicWork所有数据
	 * @param  entityMap
	 * @return List<CostClinicWork>
	 * @throws DataAccessException
	*/
	public List<CostClinicWork> queryCostClinicWork(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 查询CostClinicWorkByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostClinicWork queryCostClinicWorkByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 删除CostClinicWork
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostClinicWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 批量删除CostClinicWork
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostClinicWork(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 门诊工作量表<BR> 按月删除
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMonthlyCostClinicWork(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 门诊工作量表<BR> 更新CostClinicWork
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostClinicWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 批量更新CostClinicWork
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostClinicWork(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public CostClinicWork queryCostPatientType(Map<String,Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostClinicWorkPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
