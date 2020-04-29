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
 

public interface AssPlanDeptMapper extends SqlMapper{
	
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
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssPlanDept(Map<String,Object> entityMap)throws DataAccessException;
	public int addAssPlanDeptImport(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssPlanDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssPlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return AssPlanDept
	 * @throws DataAccessException
	*/
	public int updateBatchAssPlanDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssPlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050301 购置计划单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssPlanDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050301 购置计划单<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPlanDept> queryAssPlanDept(Map<String,Object> entityMap) throws DataAccessException;
	public List<AssPlanDept> queryAssPlanDepts(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集050301 购置计划单<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssPlanDept> queryAssPlanDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<AssPlanDept> queryAssPlanDepts(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	public List<AssPlanDept> queryAssPlanDeptByGroup(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AssPlanDept> queryAssPlanDeptByGroup(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public List<AssPlanDept> queryAssPlanDeptsByHosGroup(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<AssPlanDept> queryAssPlanDeptsByHosGroup(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取050301 购置计划单<BR> 
	 * @param  entityMap <BR>
	 *  参数为主键
	 * @return AssPlanDept
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
	/**
	 * @Description 
	 * 审核<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateToExamine(List<Map<String,Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 消审<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateNotToExamine(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 审批意见 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int examineAndApprove(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public List<AssPlanDept> queryAssPlanDeptExists(Map<String,Object> entityMap)throws DataAccessException;
	
	//往中间表加数据
	public int addAssPlanDeptImportBid(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateAssPlanDeptMoney(Map<String,Object> entityMap)throws DataAccessException;
	//购置计划主表打印
	public List<Map<String, Object>> queryAssPlanDeptBatch(
			Map<String, Object> map);
	//购置计划从表打印
	public List<Map<String, Object>> queryAssPlanDept_detail(
			Map<String, Object> map);
	public Map<String, Object> queryAssPlanDeptByPrint(Map<String, Object> map);
	public List<String> queryPlanDeptState(Map<String, Object> mapVo);
	
	
}
