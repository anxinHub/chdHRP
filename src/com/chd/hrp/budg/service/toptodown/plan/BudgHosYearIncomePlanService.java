/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.toptodown.plan;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Table:
 * BUDG_MED_INCOME_EDIT_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgHosYearIncomePlanService extends SqlService {
	
	//根据科目编码查询改科目是否已经存在
	public Integer queryBudgMedIncomeEditPlanByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String generateBudgHosYearIncomePlan(Map<String, Object> mapVo) throws DataAccessException;

}
