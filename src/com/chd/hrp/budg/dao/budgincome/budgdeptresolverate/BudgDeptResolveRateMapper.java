/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.budgdeptresolverate;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室医疗收入科目分解比例维护
 * @Table:
 * BUDG_DEPT_RESOLVE_RATE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgDeptResolveRateMapper extends SqlMapper{

	int deleteBatchgenerateResolveRateDept(List<Map<String, Object>> entityList);

	int updateBatchgenerateResolveRateDept(List<Map<String, Object>> entityList);

	void addBatchgenerateResolveRateDept(List<Map<String, Object>> entityList);
	
}
