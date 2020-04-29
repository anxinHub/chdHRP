/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationplan.plan;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * BUDG_WORK_EDIT_PLAN
 * @Table:
 * BUDG_WORK_EDIT_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkEditPlanService extends SqlService {
	
	/**
	 * 查询 方案内置BUDG_BUILT_IN表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgBuiltIn(Map<String, Object> mapVo) throws DataAccessException;

}
