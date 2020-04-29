/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostEmpBonusDetail;

/**
* @Title. @Description.
* 人员奖金明细数据表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostEmpBonusDetailService {

	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 添加CostEmpBonusDetail
	 * @param CostEmpBonusDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostEmpBonusDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 批量添加CostEmpBonusDetail
	 * @param  CostEmpBonusDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostEmpBonusDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 查询CostEmpBonusDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostEmpBonusDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 查询CostEmpBonusDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostEmpBonusDetail queryCostEmpBonusDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 删除CostEmpBonusDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostEmpBonusDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 批量删除CostEmpBonusDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostEmpBonusDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 更新CostEmpBonusDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostEmpBonusDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 批量更新CostEmpBonusDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostEmpBonusDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	//打印
	public List<Map<String,Object>> queryCostEmpBonusDetailPrint(Map<String,Object> entityMap) throws DataAccessException;
}
