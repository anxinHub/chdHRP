/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.plan;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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
 

public interface AssPlanDeptDetailMapper extends SqlMapper{
	
	public List<AssPlanDeptDetail> queryByAssPlanDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 添加050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssPlanDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssPlanDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssPlanDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return AssPlanDeptDetail
	 * @throws DataAccessException
	*/
	public int updateBatchAssPlanDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssPlanDeptDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050301 资产购置计划明细表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssPlanDeptDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050301 资产购置计划明细表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPlanDeptDetail> queryAssPlanDeptDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050301 资产购置计划明细表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPlanDeptDetail> queryAssPlanDeptDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050301 资产购置计划明细表<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssPlanDeptDetail
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

	public List<AssPlanDeptDetail> queryByAssPlanDeptId(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AssPlanDeptDetail> queryAssPlanDeptDetailByBidList(Map<String,Object> entityMap) throws DataAccessException;
	

}
