/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.compilationbasic.dpetexecute;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室业务执行数据
 * @Table:
 * BUDG_WORK_DEPT_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkDeptYearExecuteMapper extends SqlMapper{

	public List<Map<String, Object>> queryBudgDeptId(Map<String, Object> entityMap) throws DataAccessException;

	public int queryDataExists(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 末级科室 基本信息 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 指标信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryIndexData(Map<String, Object> entityMap) throws DataAccessException;
	
}
