/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.project.budgprojsetup;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
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
 

public interface BudgProjSetUpMapper extends SqlMapper{

	List<BudgProjSetUp> queryBudgProjSetUp(Map<String, Object> entityMap);

	List<BudgProjSetUp> queryBudgProjSetUp(Map<String, Object> entityMap, RowBounds rowBounds);

	int addBudgProjSetUp(Map<String, Object> mapVo);

	int deleteBudgProjSetUp(List<Map<String, Object>> listVo);

	Map<String, Object> queryBudgProjSetUpByCode(Map<String, Object> mapVo);

	int updateBudgProjSetUp(Map<String, Object> mapVo);

	int auditBudgProjSetUp(Map<String, Object> map);

	int cancelauditBudgProjSetUp(Map<String, Object> map);

	int submitBudgProjSetUp(Map<String, Object> map);

	int recallBudgProjSetUp(Map<String, Object> map);

	
}
