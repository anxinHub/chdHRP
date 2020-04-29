/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.plan;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.plan.AssPlanDept;
import com.chd.hrp.ass.entity.plan.AssPlanDeptDetail;
/**
 * 
 * @Description:
 * 050301 资产购置计划明细表
 * @Table:
 * ASS_PLAN_DEPT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssPlanDeptDetailService {
	public List<AssPlanDeptDetail> queryByAssPlanDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 添加050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAssPlanDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAssPlanDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAssPlanDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAssPlanDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAssPlanDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAssPlanDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加或者更新050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String addOrUpdateAssPlanDeptDetail(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssPlanDeptDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象050301 资产购置计划明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public AssPlanDeptDetail queryAssPlanDeptDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050301 资产购置计划明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssPlanDeptDetail
	 * @throws DataAccessException
	*/
	public AssPlanDeptDetail queryAssPlanDeptDetailByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssPlanDeptDetail> queryAssPlanDeptDetailExists(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssPlanDeptDetail> queryAssPlanDeptDetailList(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<AssPlanDeptDetail> queryAssPlanDeptDetailByBidList(Map<String,Object> entityMap) throws DataAccessException;
}
