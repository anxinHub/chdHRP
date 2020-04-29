/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.dpetexecute;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface BudgWorkDeptExecuteService extends SqlService {
	/**
	 * 指标名称下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDeptindex_code_name1(Map<String, Object> mapVo)throws DataAccessException;

	public String queryBudgDeptId(Map<String, Object> mapVo)throws DataAccessException;

	public int queryIndexCode(Map<String, Object> mapVo) throws DataAccessException;

	public int queryIndex_Name(Map<String, Object> mapVo) throws DataAccessException;

	public String budgWorkDeptMonthExecuteImport(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String,Object>> getPrintData(Map<String,Object> mapVo)throws DataAccessException;



}
