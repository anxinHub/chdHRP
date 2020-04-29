/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
*  门诊科室对比分析
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostClinicWorkContrastAnalysisService {

	/**
	 * @Description 
	 * 门诊科室对比分析
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostClinicWorkContrast(Map<String,Object> entityMap) throws DataAccessException;
	
}
