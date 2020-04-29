/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.accreconciliation;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface AccReconciliationByAssMapper extends SqlMapper{
	
	public List<Map<String,Object>> queryAccReconciliationByAss(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}
