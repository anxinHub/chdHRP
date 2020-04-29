/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.reportforms.query;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 医院及科室医疗收入预算 报表查询
 * @Table:
 * BUDG_MED_INCOME_HOS_YEAR  BUDG_MED_INCOME_HOS_MONTH  BUDG_MED_INCOME_DEPT_MONTH   BUDG_MED_INCOME_DEPT_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedInQueryMapper extends SqlMapper{
	
	/**
	 * 医院医疗收入预算 报表查询(不分页)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInHosBudg(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 医院医疗收入预算 报表查询(分页)
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInHosBudg(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 科室医疗收入预算 报表查询(不分页)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInDeptBudg(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科室医疗收入预算 报表查询(分页)
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedInDeptBudg(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	
}
