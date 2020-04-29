/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.compilationbasic.dpetexecute;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
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
 

public interface BudgWorkDeptExecuteMapper extends SqlMapper{
	/**
	 * 指标名称下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptindex_code_name1(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 科室下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptId(Map<String, Object> mapVo, RowBounds rowBounds)throws DataAccessException;
	public int queryIndexCode(Map<String, Object> mapVo)throws DataAccessException;
	public int queryIndex_Name(Map<String, Object> mapVo)throws DataAccessException;
	public int queryDataExists(Map<String, Object> item) throws DataAccessException;

}
