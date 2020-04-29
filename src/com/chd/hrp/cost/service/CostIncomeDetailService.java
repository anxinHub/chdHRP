/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 科室收入数据明细表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIncomeDetailService {

	
	/**
	 * @Description 
	 * 科室收入数据明细表<BR> 查询CostIncomeDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostIncomeDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 科室收入数据明细表<BR> 批量删除CostIncomeDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostIncomeDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 采集HIS数据
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostIncomeDetailByHis(Map<String, Object> entityMap)throws DataAccessException;
	
	
	public String impCostIncomeDetail(Map<String, Object> mapVo) throws DataAccessException;
}
