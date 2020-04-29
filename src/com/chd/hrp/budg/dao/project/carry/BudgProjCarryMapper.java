/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.project.carry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
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
 

public interface BudgProjCarryMapper extends SqlMapper{

	String queryCarryYear(Map<String, Object> entityMap);

	List<Map<String, Object>> queryBudgProjCarryByYear(Map<String, Object> entityMap);

	int addBudgProjYear(Map<String, Object> entityMap);


	int updateBudgProjYear(Map<String, Object> saveMap);

	int updatebudgProjCarry(Map<String, Object> entityMap);

	int updatecarryBudgProj(Map<String, Object> entityMap);

	int addcarryBudgProj(Map<String, Object> entityMap);

	String queryCarryYearByYear(Map<String, Object> entityMap);

	List<Map<String, Object>> querybudgProjDetailYearByYear(Map<String, Object> entityMap);

	Map<String, Object> queryDetaiByCode(Map<String, Object> detailmap);

	int addBudgProjDetaiYear(Map<String, Object> saveMap);

	int updateBudgProjDetaiYear(Map<String, Object> saveMap);
	
}
