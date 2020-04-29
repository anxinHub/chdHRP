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
* 科室收入数据总表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostIncomeMainService {

	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 查询CostIncome分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostIncomeMain(Map<String,Object> entityMap) throws DataAccessException;
	

	
	/**
	 * @Description 
	 * 科室收入数据总表<BR> 批量删除CostIncome
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostIncomeMain(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 采集HIS数据
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostIncomeMainByHis(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 赞着核算年月删除数据
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteMonthlyCostIncomeMain(Map<String, Object> entityMap)throws DataAccessException;
	
	public String impCostIncomeMain(Map<String, Object> map)throws DataAccessException;
}
