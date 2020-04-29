
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

import com.chd.hrp.ass.entity.apply.AssApplyPlanMap;
/**
 * 
 * @Description:
 * 050201 购置申请与计划关系表
 * @Table:
 * ASS_APPLY_PLAN_MAP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AssApplyPlanMapMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加050201 购置申请与计划关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAssApplyPlanMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加050201 购置申请与计划关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAssApplyPlanMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	 /**
	 * @Description 
	 * 批量删除050201 购置申请与计划关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAssApplyPlanMap(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 批量删除050201 购置申请与计划关系表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteBatchAssApplyPlanMap(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050201 购置申请与计划关系表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssApplyPlanMap> queryAssApplyPlanMap(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集050201 购置申请与计划关系表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AssApplyPlanMap> queryAssApplyPlanMap(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
}
