package com.chd.hrp.mat.service.account.report;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description:
 * 预算降本报表
 * @Table:
 * @Author: weixiaofeng
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAccountReportReducedBudgService {
	
	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryReducedBudg(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 报表打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryReducedBudgPrint(Map<String, Object> entityMap)
			throws DataAccessException;
}
