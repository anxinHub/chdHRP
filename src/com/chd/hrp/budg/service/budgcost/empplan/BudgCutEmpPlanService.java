/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgcost.empplan;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 状态（STATE），“01新建、02已审核”
计划类型（PLAN_TYPE)：01年度计划02追加计划
 * @Table:
 * BUDG_CUT_EMP_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgCutEmpPlanService extends SqlService {
	
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
	 * 查询数据信息  返回修改页面
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryDataByCode(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 职工名称下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgEmpName(Map<String, Object> mapVo)throws DataAccessException;
	
}
