/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.reportforms.query;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医院及科室 医疗收入预算 报表查询
 * @Table:
 * BUDG_MED_INCOME_HOS_MONTH  BUDG_MED_INCOME_DEPT_MONTH 
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedInQueryService extends SqlService {
	
	/**
	 * 医院医疗收入预算 报表查询
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInHosBudg(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 科室 医疗收入预算 报表查询
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedInDeptBudg(Map<String, Object> map) throws DataAccessException;
	/**
	 * 科室 医疗收入预算 报表打印
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedInQueryPrintDate(Map<String,Object> mapVo) throws DataAccessException;
	/**
	 * 医院医疗收入预算 报表打印
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryHosMedInQueryPrintDate(Map<String,Object> mapVo) throws DataAccessException;
	

}
