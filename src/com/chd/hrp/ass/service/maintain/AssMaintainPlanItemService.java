/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.maintain;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanItem;
/**
 * 
 * @Description:
 * 051202 保养计划项目明细
 * @Table:
 * ASS_MAINTAIN_PLAN_ITEM
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssMaintainPlanItemService {

	/**
	 * @Description 
	 * 添加051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssMaintainPlanItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssMaintainPlanItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssMaintainPlanItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssMaintainPlanItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssMaintainPlanItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssMaintainPlanItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssMaintainPlanItem(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssMaintainPlanItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象051202 保养计划项目明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssMaintainPlanItem queryAssMaintainPlanItemByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051202 保养计划项目明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMaintainPlanItem
	 * @throws DataAccessException
	*/
	public AssMaintainPlanItem queryAssMaintainPlanItemByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取051202 保养计划项目明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMaintainPlanItem>
	 * @throws DataAccessException
	*/
	public List<AssMaintainPlanItem> queryAssMaintainPlanItemExists(Map<String,Object> entityMap)throws DataAccessException;

	public List<AssMaintainPlanItem> queryAssMaintainPlanItemByPlans(Map<String,Object> entityMap)throws DataAccessException;
}
