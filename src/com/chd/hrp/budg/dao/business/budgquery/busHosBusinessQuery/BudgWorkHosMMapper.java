/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.budgquery.busHosBusinessQuery;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgWorkHosMonth;
/**
 * 
 * @Description:
 * 医院月份业务预算
 * @Table:
 * BUDG_WORK_HOS_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkHosMMapper extends SqlMapper{

	public List<Map<String,Object>> queryBudgWorkLinkMonth(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryBudgWorkLinkMonth(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String,Object>> queryHosYearCopy(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryHosYearCopy(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String,Object>> queryHosMonthCopy(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryHosMonthCopy(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String,Object>> queryGroupYearCopy(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryGroupYearCopy(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String,Object>> queryGroupMonthCopy(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryGroupMonthCopy(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
