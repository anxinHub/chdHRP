/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.downtotop.deptyearbudg;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.sys.entity.Dept;
/**
 * 
 * @Description:
 * 科室年度医疗收入独立核算科目预算
 * @Table:
 * BUDG_DEPT_INDEPENDENT_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgDeptIndependentSubjMapper extends SqlMapper{

	public Map<String, Object> queryLastYearIncome(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String, Object> queryGetWay(Map<String, Object> entityMap) throws DataAccessException;

	public int queryBudgMedIncomeEditPlanByCode(Map<String, Object> mapVo) throws DataAccessException;

	public String queryGetWayFromBudgMedIncomeEditPlan(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String,Object>> queryBudgDept(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;

	public String queryDataExist(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 增长比例获取
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> getGrowRate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 科室年度医疗收入独立核算科目预算  更新 增长比例 及相关数据数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int updateGrowRate(List<Map<String, Object>> listVo) throws DataAccessException;

	public List<Map<String, Object>> queryDeptId(Map<String, Object> mapVo)throws DataAccessException;

	public List<Map<String, Object>> queryDataDeptList(Map<String, Object> mapVo)throws DataAccessException;
	
}
