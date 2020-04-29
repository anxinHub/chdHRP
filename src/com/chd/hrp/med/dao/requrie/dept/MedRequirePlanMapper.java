/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.requrie.dept;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedRequireDetail;
import com.chd.hrp.med.entity.MedRequireMain;
/**
 * 
 * @Description:
 * 科室需求计划编制页面
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedRequirePlanMapper extends SqlMapper{
	/**
	 * 科室需求计划编制--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryPlan(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryPlan(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 科室需求计划编制--终止计划
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int updateAbortMedDeptPlan(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 科室需求计划编制--提交单据
	 * @param entityList
	 * @return
	 */
	public int updateSubmitMedDeptPlan(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 科室需求计划编制--取消提交
	 * @param entityList
	 * @return
	 */
	public int updateUnSubmitMedDeptPlan(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 科室需求计划编制--科室消耗导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryDeptExpend(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 科室需求计划编制--库房导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryDeptSupport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 库存安全导入--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryStoreSafe(Map<String, Object> entityMap) throws DataAccessException;

	
	
	
}
