/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.accreconciliation;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface AccReconciliationByAssService {
	
	public String queryAccReconciliationByAss(Map<String,Object> entityMap) throws DataAccessException;
	
}
