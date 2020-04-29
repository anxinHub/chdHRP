/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.toptodown.hosyearinbudg;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 医院年度医疗收入独立核算科目预算
 * @Table:
 * BUDG_HOS_INDEPENDENT_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgHosIndependentSubjService extends SqlService {

	public String queryLastYearIncome(Map<String, Object> mapVo) throws DataAccessException;

	public int queryBudgMedIncomeEditPlanByCode(Map<String, Object> mapVo) throws DataAccessException;

	public String queryGetWayFromBudgMedIncomeEditPlan(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医院年度收入预算  确定预算 计算
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectCertenHYBudgData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存数据 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 查询要生成的数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医院年度医疗收入预算增量预算  更新 增长比例 及相关数据数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateGrowRate(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 增长比例获取
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String getGrowRate(Map<String, Object> mapVo) throws DataAccessException;

	public String queryBudgSubjEditMethod(Map<String, Object> mapVo)throws DataAccessException;

}
