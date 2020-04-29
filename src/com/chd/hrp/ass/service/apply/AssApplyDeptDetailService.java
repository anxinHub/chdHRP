/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.apply;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.apply.AssApplyDeptDetail;
import com.chd.hrp.ass.entity.plan.AssPlanDeptDetail;
/**
 * 
 * @Description:
 * 050201 资产购置申请明显表
 * @Table:
 * ASS_APPLY_DEPT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssApplyDeptDetailService {
	public List<AssApplyDeptDetail> queryByAssApplyDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 添加050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssApplyDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssApplyDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssApplyDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssApplyDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssApplyDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssApplyDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssApplyDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集050201 资产购置申请明显表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssApplyDeptDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050201 资产购置申请明显表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssApplyDeptDetail queryAssApplyDeptDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050201 资产购置申请明显表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssApplyDeptDetail
	 * @throws DataAccessException
	*/
	public AssApplyDeptDetail queryAssApplyDeptDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;

	public String queryAssApplyDeptDetailByUpdate(Map<String, Object> page);
	
	public List<AssApplyDeptDetail> queryAssApplyDeptDetailExists(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssApplyDeptDetail>queryByAssApplyDeptDetailByPlanDept(Map<String,Object> entityMap)throws DataAccessException;
}
