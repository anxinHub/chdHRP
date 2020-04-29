/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.dao.autovouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.autovouch.AccPatientType;

public interface AccAutoHisLogMainMapper extends SqlMapper {

	public List<Map<String, Object>> queryAccHisViewLog(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccHisViewLog(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryAutoHisViewSetting(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAutoHisViewSetting(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public int queryById(Map<String,Object> entityMap)throws DataAccessException;
	public int queryDetailById(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchDetail(List<?> entityMap)throws DataAccessException;
}
