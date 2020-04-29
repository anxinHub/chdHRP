
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.dao.apply;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDept;
/**
 * 
 * @Description:
 * 050201 购置申请单
 * @Table:
 * ASS_APPLY_DEPT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssApplyDeptMapper extends SqlMapper{
	
	/**
	 * 获取当前的序列号
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryAssApplyDeptSequence()throws DataAccessException;
	/**
	 * @Description 
	 * 添加050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssApplyDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssApplyDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAssApplyDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return AssApplyDept
	 * @throws DataAccessException
	*/
	public int updateBatchAssApplyDept(List<Map<String, Object>> entityMap)throws DataAccessException;
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
	 * 删除050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssApplyDept(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAssApplyDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050201 购置申请单<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssApplyDept> queryAssApplyDept(Map<String,Object> entityMap) throws DataAccessException;
	public List<AssApplyDept> queryAssApplyDepts(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050201 购置申请单<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssApplyDept> queryAssApplyDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<AssApplyDept> queryAssApplyDepts(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<AssApplyDept> queryAssApplyDeptByPlanDept(Map<String,Object> entityMap) throws DataAccessException;
	public List<AssApplyDept> queryAssApplyDeptByPlanDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return AssApplyDept
	 * @throws DataAccessException
	*/
	public AssApplyDept queryAssApplyDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取050201 购置申请单<BR> 
	 * @param  entityMap
	 * @return AssApplyDept
	 * @throws DataAccessException
	 */
	public AssApplyDept queryAssApplyDeptByUniqueness(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<AssApplyDept> queryAssApplyDeptExists(Map<String,Object> entityMap)throws DataAccessException;
	//打印主表
	public List<Map<String, Object>> queryMatCheckByPrintBatch(
			Map<String, Object> map);
	//打印从表
	public List<Map<String, Object>> queryMatCheckByPrintBatch_detail(
			Map<String, Object> map);
	public Map<String, Object> queryMatCheckByPrint(Map<String, Object> map);
	public List<String> queryAssApplyDeptState(Map<String, Object> mapVo);
	
}
