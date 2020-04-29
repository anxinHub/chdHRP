/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostEmpWageDetail;

/**
* @Title. @Description.
* 人员工资明细数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpWageDetailService {

	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 添加CostEmpWageDetail
	 * @param CostEmpWageDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostEmpWageDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 批量添加CostEmpWageDetail
	 * @param  CostEmpWageDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostEmpWageDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 查询CostEmpWageDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostEmpWageDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 查询CostEmpWageDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostEmpWageDetail queryCostEmpWageDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 删除CostEmpWageDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostEmpWageDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 批量删除CostEmpWageDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostEmpWageDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 更新CostEmpWageDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostEmpWageDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 批量更新CostEmpWageDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostEmpWageDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostEmpWageDetailPrint(Map<String,Object> entityMap) throws DataAccessException;
}
