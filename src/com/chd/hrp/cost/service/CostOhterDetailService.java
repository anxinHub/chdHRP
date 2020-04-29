/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostOhterDetail;

/**
* @Title. @Description.
* 科室其他费用表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostOhterDetailService {

	/**
	 * @Description 
	 * 科室其他费用表<BR> 添加CostOhterDetail
	 * @param CostOhterDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostOhterDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 批量添加CostOhterDetail
	 * @param  CostOhterDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostOhterDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 查询CostOhterDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostOhterDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 查询CostOhterDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostOhterDetail queryCostOhterDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 删除CostOhterDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostOhterDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 批量删除CostOhterDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostOhterDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 更新CostOhterDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostOhterDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室其他费用表<BR> 批量更新CostOhterDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostOhterDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	public List<Map<String,Object>> queryCostOhterDetailPrint(Map<String,Object> entityMap) throws DataAccessException;
}
