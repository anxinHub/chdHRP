/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.plan;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.apply.AssApplyDept;
import com.chd.hrp.ass.entity.plan.AssPlanDept;
/**
 * 
 * @Description:
 * 050301 购置计划单
 * @Table:
 * ASS_PLAN_DEPT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssPlanDeptService {

	/**
	 * 获取当前的序列号
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryAssPlanDeptSequence()throws DataAccessException;
	/**
	 * @Description 
	 * 添加050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssPlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addAssPlanDeptImport(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 批量添加050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssPlanDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssPlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssPlanDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssPlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssPlanDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssPlanDept(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssPlanDept(Map<String,Object> entityMap) throws DataAccessException;
	public String queryAssPlanDepts(Map<String,Object> entityMap) throws DataAccessException;
	public String queryAssPlanDeptByGroup(Map<String,Object> entityMap) throws DataAccessException;
	public String queryAssPlanDeptsByHosGroup(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询对象050301 购置计划单<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssPlanDept queryAssPlanDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050301 购置计划单<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssPlanDept
	 * @throws DataAccessException
	*/
	public AssPlanDept queryAssPlanDeptByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	//审核
	public String updateToExamine(List<Map<String,Object>> entityMap)throws DataAccessException;
	//销审
	public String updateNotToExamine(List<Map<String,Object>> entityMap)throws DataAccessException;
		
	public String examineAndApprove(List<Map<String,Object>> entityMap)throws DataAccessException;
		
	public List<AssPlanDept> queryAssPlanDeptExists(Map<String,Object> entityMap)throws DataAccessException;
	//往中间表加数据
	public String addAssPlanDeptImportBid(Map<String,Object> entityMap)throws DataAccessException;
	Map<String, Object> queryAssPlanDeptDY(Map<String, Object> map)
			throws DataAccessException;
	public List<String> queryPlanDeptState(Map<String, Object> mapVo)throws DataAccessException;
	
}
