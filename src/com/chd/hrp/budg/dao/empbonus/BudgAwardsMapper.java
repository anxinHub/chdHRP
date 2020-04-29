/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.empbonus;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 
 * @Table:
 * BUDG_Awards_ITEM_ADJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgAwardsMapper extends SqlMapper{

	public List<Map<String, Object>> queryLYEmpCountFromTotal(Map<String, Object> map) throws DataAccessException;

	public Map<String, Object> queryAddPlan(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryCutPlan(Map<String, Object> mapVo) throws DataAccessException;

	public int queryEmpExists(Map<String, Object> map) throws DataAccessException;

	public List<Map<String, Object>> queryEmpTypeAwards(Map<String, Object> mapVo) throws DataAccessException;

	public Map<String, Object> queryTypeAwardsAdj(Map<String, Object> typeAwardsMap) throws DataAccessException;
	
}
