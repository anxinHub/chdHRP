/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.dict;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.dict.AssShareDeptCost;
/**
 * 
 * @Description:
 * 050807 分摊科室设置
 * @Table:
 * ASS_SHARE_DEPT_COST
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssShareDeptCostService {

	/**
	 * @Description 
	 * 添加050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssShareDeptCost(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssShareDeptCost(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssShareDeptCost(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssShareDeptCost(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssShareDeptCost(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssShareDeptCost(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssShareDeptCost(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集050807 分摊科室设置<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssShareDeptCost(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050807 分摊科室设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssShareDeptCost queryAssShareDeptCostByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050807 分摊科室设置<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssShareDeptCost
	 * @throws DataAccessException
	*/
	public AssShareDeptCost queryAssShareDeptCostByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
}
