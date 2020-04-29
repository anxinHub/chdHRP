/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgcash;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 资金计划
 * @Table:
 * BUDG_DEPT_RESOLVE_RATE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgCashPlanService extends SqlService {
	
	/**
	 * @Description 
	 * 查询数据 现金流量项目下拉框
	 */
	public String queryCashItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * @Description 
	 * 生成 预算调整单号
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String setBudgApplyCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键  查询资金计划明细
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgCashPlanDet(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 审核     
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateReviewState(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 销审     
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateCancelBatch(List<Map<String, Object>> listVo) throws DataAccessException;
	/**
	 * 最新导入
	 */
	public String importBudgCashPlan(Map<String, Object> mapVo) throws DataAccessException;
}
