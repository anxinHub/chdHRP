/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostClinicWork;

/**
* @Title. @Description.
* 门诊工作量表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostClinicWorkService {

	/**
	 * @Description 
	 * 门诊工作量表<BR> 添加CostClinicWork
	 * @param CostClinicWork entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostClinicWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 批量添加CostClinicWork
	 * @param  CostClinicWork entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostClinicWork(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 查询CostClinicWork分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostClinicWork(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 查询CostClinicWorkByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostClinicWork queryCostClinicWorkByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 删除CostClinicWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostClinicWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 批量删除CostClinicWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostClinicWork(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 按月删除CostClinicWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteMonthlyCostClinicWork(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 门诊工作量表<BR> 更新CostClinicWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostClinicWork(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 门诊工作量表<BR> 批量更新CostClinicWork
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostClinicWork(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public CostClinicWork queryCostPatientType(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String readAssFinaDictFiles(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String,Object>> queryCostClinicWorkPrint(Map<String,Object> entityMap) throws DataAccessException;
}
