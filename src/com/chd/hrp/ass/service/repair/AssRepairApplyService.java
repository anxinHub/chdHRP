/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.repair;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlan;
import com.chd.hrp.ass.entity.repair.AssRepairApply;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REPAIR_APPLY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssRepairApplyService extends SqlService {

	String auditAssRepairApply(List<Map<String, Object>> listVo);

	String queryAssRepairApply(Map<String, Object> page);

	AssRepairApply queryAssMaintainPlanByCode(Map<String, Object> mapVo);

	

}
