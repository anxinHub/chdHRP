/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.cost.service;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 成本_科室成本核算总表
 * @Table:
 * COST_DEPT_COST
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface CostDeptCostCheckService extends SqlService {
	/**
	 * @Description 
	 * 科室分摊计算方法
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public String check(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 科室分摊计算方法
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public String checkH2Db(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
}
