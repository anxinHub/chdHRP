/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 成本构成变化趋势<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostConstituteChangeAnalysisService {

	/**
	 * @Description 
	 * 成本构成变化趋势<BR> 查询CostConstituteChange分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostConstituteChange(Map<String,Object> entityMap) throws DataAccessException;
	
}
