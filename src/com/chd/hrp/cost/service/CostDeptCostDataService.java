/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.service;

import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostDeptCostData;

/**
 * @Title. @Description. 科室成本核算表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface CostDeptCostDataService {

	public String deptCostShareData(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCostDeptCostData(Map<String, Object> entityMap) throws DataAccessException;

	public CostDeptCostData queryCostDeptCostDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCostStructureAnalysis(Map<String, Object> entityMap) throws DataAccessException;

}
