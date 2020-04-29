package com.chd.hrp.hr.dao.organize;


import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HosStation;

public interface HosStationInfoMapper  extends SqlMapper{

	public void deleteBatchStationInfo(List<HosStation> entityList) throws DataAccessException;

	public List<HosStation> queryInfoByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HosStation> queryInfoByName(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryStationInfoPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 部门信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDeptInfoByCode(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryStationInfoList(Map<String, Object> entityMap);
    
}
