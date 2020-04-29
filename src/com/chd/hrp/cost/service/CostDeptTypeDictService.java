/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.cost.entity.CostDeptTypeDict;

/**
* @Title. @Description.
* 成本类型字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostDeptTypeDictService {

	/**
	 * @Description 
	 * 成本类型字典<BR> 添加CostDeptTypeDict
	 * @param CostDeptTypeDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostDeptTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本类型字典<BR> 批量添加CostDeptTypeDict
	 * @param  CostDeptTypeDict entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostDeptTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本类型字典<BR> 查询CostDeptTypeDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostDeptTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本类型字典<BR> 查询CostDeptTypeDictByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostDeptTypeDict queryCostDeptTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本类型字典<BR> 删除CostDeptTypeDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostDeptTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本类型字典<BR> 批量删除CostDeptTypeDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostDeptTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本类型字典<BR> 更新CostDeptTypeDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostDeptTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本类型字典<BR> 批量更新CostDeptTypeDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostDeptTypeDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 成本类型 通过编码获取成本类型
	 * @param  entityMap 
	 * @return CostDeptTypeDict
	 * @throws DataAccessException
	 */
	public CostDeptTypeDict queryCostDeptTypeDictByTypeCode(Map<String,Object> entityMap)throws DataAccessException;
	
}
