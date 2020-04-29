/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.empbonus;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * BUDG_EMP_TYPE_AWARDS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgEmpTypeAwardsMapper extends SqlMapper{
	/**
	 * @Description 
	 * 按年度生成奖金平均值
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	int collectBudgYearEmpTypeAwards(List<Map<String, Object>> listVo);

	List<Map<String, Object>> queryBudgEmpAwardsTotalByYear(Map<String, Object> mapVo);
	
}
