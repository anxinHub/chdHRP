/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgcost.proj;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 教学项目支出预算 /教学项目支出预算执行 
 * @Table:
 * BUDG_PROJ_DETAIL_YEAR 
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgTeachProjCostMapper extends SqlMapper{
	
	/**
	 * 教学项目支出预算 查询   不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgTeachProjCost( Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 教学项目支出预算  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgTeachProjCost( Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 教学项目支出预算执行 查询 不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgTeachProjCostExe(Map<String, Object> entityMap);
	/**
	 * 教学项目支出预算执行  查询   分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgTeachProjCostExe(Map<String, Object> entityMap, RowBounds rowBounds);

	
	
}
