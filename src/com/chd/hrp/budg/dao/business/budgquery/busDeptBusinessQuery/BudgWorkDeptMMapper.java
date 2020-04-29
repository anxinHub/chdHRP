/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.budgquery.busDeptBusinessQuery;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgWorkHosMonth;
/**
 * 
 * @Description:
 * 科室月份业务预算
 * @Table:
 * BUDG_WORK_DEPT_MONTH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkDeptMMapper extends SqlMapper{

	public List<BudgWorkHosMonth> queryDeptMonthCopy(Map<String, Object> entityMap) throws DataAccessException;

	public List<BudgWorkHosMonth> queryDeptMonthCopy(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<BudgWorkHosMonth> queryDeptYeCopy(Map<String, Object> entityMap) throws DataAccessException;

	public List<BudgWorkHosMonth> queryDeptYeCopy(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
