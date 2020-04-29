/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.requrie.dept;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.mat.entity.MatRequireMain;
/**
 * 
 * @Description:
 * 科室需求计划编制
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatRequirePlanService extends SqlService{
	/**
	 * 科室需求计划编制--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPlan(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 终止计划
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateAbortMatDeptPlan(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 提交单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateSubmitMatDeptPlan(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 取消提交
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateUnSubmitMatDeptPlan(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 删除购置计划
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteMatDeptPlan(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 科室消耗导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptExpend(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 仓库配套导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptSupport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 安全库存导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryStoreSafe(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 复制购置计划单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String copyMatDeptPlan(List<Map<String, Object>> entityList) throws DataAccessException;

	public Map<String, Object> printMatRequireMainData(Map<String, Object> entityMap) throws DataAccessException;

	public void updateMatApplyRelaTables(Map<String, Object> entityMap) throws DataAccessException;


		
}
