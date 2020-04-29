/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.project.carry;
import java.util.Map;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 项目预算年末结转
 * @Table:
 * BUDG_PROJ_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgProjCarryService  {

	String queryCarryYear(Map<String, Object> entityMap);

	String carryBudgProjCarry(Map<String, Object> entityMap);

	String reCarryBudgProjCarry(Map<String, Object> mapVo);

}
