/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.downtotop.deptyearbudg;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface BudgDeptIndependentSubjService extends SqlService {
	
	
	/**
	 * 根据科目编码 查询上年医院年度收入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryLastYearIncome(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据科目编码  查询数据是否已经在数据库存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBudgMedIncomeEditPlanByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据封装条件  查询该科目的取值方法
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryGetWayFromBudgMedIncomeEditPlan(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDept(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 *  科室年度医疗收入预算 确定预算 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectCertenDYBudgData(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException ;

	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 增长比例获取
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String getGrowRate(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 科室年度医疗收入预算增量预算  更新 增长比例 及相关数据数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateGrowRate(List<Map<String, Object>> listVo) throws DataAccessException;
 
	public List<Map<String, Object>> queryDeptId(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询要生成的部门
     * @param mapVo
     * @return
     */
	public List<Map<String, Object>> queryDataDeptList(Map<String, Object> mapVo);
}
