/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostDriDetail;

/**
* @Title. @Description.
* 科室成本明细数据表_平级分摊<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDriDetailService {

	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 添加CostDriDetail
	 * @param CostDriDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostDriDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 批量添加CostDriDetail
	 * @param  CostDriDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostDriDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 查询CostDriDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostDriDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 查询CostDriDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostDriDetail queryCostDriDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 删除CostDriDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostDriDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 批量删除CostDriDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostDriDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 更新CostDriDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostDriDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室成本明细数据表_平级分摊<BR> 批量更新CostDriDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostDriDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
