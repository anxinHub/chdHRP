
package com.chd.hrp.hip.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hip.entity.HipDataType;
import com.chd.hrp.hip.entity.HipSyncLog;

public interface DataTypeService {

	public String queryHipDataType(Map<String, Object> map) throws DataAccessException;

	public String queryHrpTable(Map<String, Object> map) throws DataAccessException;

	public String queryHrpTableColumn(Map<String, Object> map) throws DataAccessException;
	
	public String queryHipSyncLog(Map<String, Object> map) throws DataAccessException;
			

	public List<HipDataType> queryHipDataTypeBySql(Map<String, Object> map) throws DataAccessException;

	public int saveHipSyncLog(List<Map<String, Object>> list) throws DataAccessException;
	
	public String clearHipSyncLog(Map<String, Object> map) throws DataAccessException;

	
	public String saveHipDataType(Map<String, Object> map) throws DataAccessException;
	
	
	public String deleteHipDataType(List<Map<String, Object>> listMap) throws DataAccessException;
	
	public HipDataType queryHipDataTypeByCode(Map<String, Object> map) throws DataAccessException;
	
	public HipDataType queryHipDataTypeById(Map<String, Object> map) throws DataAccessException;

	public String queryHipDataDecode(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String, Object> addHipDataDecode(Map<String, Object> entityMap) throws DataAccessException;

	public String saveHrpData(Map<String, Object> mapVo,HipDataType dataType, List<Map<String, Object>> list)
			throws DataAccessException;
	
	public String querySourceColListByType(Map<String, Object> entityMap) throws DataAccessException;
	public String queryHrpDictTable(Map<String, Object> entityMap) throws DataAccessException;
	public String queryHrpDictTableCol(Map<String, Object> entityMap) throws DataAccessException;
	
	public HipSyncLog queryHipSyncLogByTypeId(Map<String, Object> entityMap) throws DataAccessException;
}
