package com.chd.hrp.hr.dao.organize;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HosDutyLevel;
import com.chd.hrp.hr.entity.orangnize.HosStation;
import com.chd.hrp.hr.entity.orangnize.HosStationLevel;

public interface HosStationLevelMapper  extends SqlMapper{

	public List<HosStationLevel> queryHrStationLevelByCode(Map<String, Object> entityMap)throws DataAccessException;

	public List<HosStationLevel> queryStationLevel(Map<String, Object> entityMap) throws DataAccessException;

	public List<HosStationLevel> queryStationLevel(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 批量删除
	 * @param entityList
	 */
	public void deleteBatchHrStationLevel(List<HosStationLevel> entityList) throws DataAccessException;

	public List<HosStationLevel> queryHrStationLevelByName(Map<String, Object> entityMap) throws DataAccessException;

	public HosStationLevel queryDutyLevelByCode(Map<String, Object> entityMap) throws DataAccessException;

	public List<HosStationLevel> queryListLevel(List<HosStationLevel> listVo);

	public List<Map<String, Object>> queryStationLevelList(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryStationLevelByPrint(Map<String, Object> entityMap);
	
    
}
