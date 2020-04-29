package com.chd.hrp.hip.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hip.entity.DataDecode;
import com.chd.hrp.hip.entity.HipDataType;
import com.chd.hrp.hip.entity.HipSyncLog;

public interface DataTypeMapper extends SqlMapper {

	
	public List<Map<String,Object>> queryHipDataType(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String,Object>> queryHrpTable(Map<String, Object> map) throws DataAccessException;
	public List<Map<String,Object>> queryHrpTable(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String,Object>> queryHrpTableColumn(Map<String, Object> map) throws DataAccessException;
	
	public List<HipSyncLog> queryHipSyncLog(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	public List<HipDataType> queryHipDataTypeBySql(Map<String, Object> map) throws DataAccessException;
	
	public int saveHipSyncLog(List<Map<String, Object>> list) throws DataAccessException;
	
	public int addHipDataType(Map<String, Object> map) throws DataAccessException;
	
	public int updateHipDataType(Map<String, Object> map) throws DataAccessException;
	
	public int deleteHipDataType(List<Map<String, Object>> listMap) throws DataAccessException;
	
	public int deleteBatchHipSyncLog(List<Map<String, Object>> listMap) throws DataAccessException;
	
	public int deleteBatchHipDataDecode(List<Map<String, Object>> listMap) throws DataAccessException;
	
	public HipDataType queryHipDataTypeByCode(Map<String, Object> map) throws DataAccessException;
	
	public HipDataType queryHipDataTypeById(Map<String, Object> map) throws DataAccessException;
	
	public List<DataDecode> queryHipDataDecodeById(@Param(value="type_id") Long typeId) throws DataAccessException;
	
	public List<Map<String, Object>> queryHipDataDecodeList(Map<String, Object> map) throws DataAccessException;
	
	public int addHipDataDecode(List<Map<String, Object>> listMap) throws DataAccessException;

	public int deleteHipDataDecode(Map<String, Object> map) throws DataAccessException;
	
	public int deleteHrpData(@Param(value="map") Map<String,Object> map,@Param(value="list") List<Map<String,Object>> list) throws DataAccessException;
	
	public int saveHrpData(@Param(value="map") Map<String,Object> map,@Param(value="list") List<Map<String,Object>> list) throws DataAccessException;
	
	public List<Map<String,Object>> queryDataList(@Param(value="sql") String sql) throws DataAccessException;
	
	public int deleteData(List<Map<String,Object>> list) throws DataAccessException;
	
	public String queryQSqlByType(Map<String, Object> map) throws DataAccessException;
	
	public int deleteHipSyncLog(Map<String, Object> map) throws DataAccessException;
	
	public HipSyncLog queryHipSyncLogByTypeId(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String,Object>> queryHrpTableColumnType(@Param(value="table_name") String tableName) throws DataAccessException;
	
	public int queryDataCount(@Param(value="type_id") Long typeId) throws DataAccessException;
}
