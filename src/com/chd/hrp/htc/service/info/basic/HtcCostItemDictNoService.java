package com.chd.hrp.htc.service.info.basic;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 成本项目变更表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface HtcCostItemDictNoService {

	
	
	/**
	 * @Description 
	 * 成本项目变更表<BR> 查询CostItemDictNo分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryHtcCostItemDictNo(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
}
