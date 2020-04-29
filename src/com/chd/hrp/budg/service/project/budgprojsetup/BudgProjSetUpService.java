/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.project.budgprojsetup;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
import com.chd.hrp.budg.entity.BudgProjSetUp;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * BUDG_PROJ_SET_UP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgProjSetUpService extends SqlService {

	String queryBudgProjSetUp(Map<String, Object> page);

	String addBudgProjSetUp(Map<String, Object> mapVo);

	String generateApplyCode(Map<String, Object> entityMap);

	String deleteBudgProjSetUp(List<Map<String, Object>> listVo);

	Map<String, Object> queryBudgProjSetUpByCode(Map<String, Object> mapVo);

	String updateBudgProjSetUp(Map<String, Object> mapVo);

	String auditBudgProjSetUp(List<Map<String, Object>> listVo);

	String cancelauditBudgProjSetUp(List<Map<String, Object>> listVo);

	String submitBudgProjSetUp(List<Map<String, Object>> listVo);

	String recallBudgProjSetUp(List<Map<String, Object>> listVo);



}
