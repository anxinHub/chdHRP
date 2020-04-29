/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostAllocationProcess;

/**
* @Title. @Description.
* 科室成本明细数据表_医辅分摊<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostAllocationProcessService {

	/**
	 * @Description 
	 * 科室成本明细数据表_医辅分摊<BR> 批量添加CostAllocationProcess
	 * @param  CostAllocationProcess entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostAllocationProcess(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_医辅分摊<BR> 删除CostAllocationProcess
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostAllocationProcess(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊过程数据查询
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostAllocationProcess(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 分摊过程数据查询
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryCostAllocationProcessPrint(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
}
