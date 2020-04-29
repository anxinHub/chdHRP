/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgcost.empplan;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 状态（STATE），“01新建、02已审核”
计划类型（PLAN_TYPE)：01年度计划02追加计划
 * @Table:
 * BUDG_ADD_EMP_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgAddEmpPlanMapper extends SqlMapper{
	
	/**
	 * 需求科室下拉框
	 */
	public List<Map<String,Object>> queryBudgHosDept(Map<String, Object> mapVo);
	
	/**
	 * 查询数据状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryState(Map<String, Object> mapVo) throws DataAccessException;
		
	
	/**
	 * 审核销审更新数据状态
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateReviewState(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 查询数据  返回修改页面
	 * @param entityList
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDataByCode(Map<String, Object> mapVo);

}
