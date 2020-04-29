package com.chd.hrp.hr.dao.organize;


import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HosDuty;
import com.chd.hrp.hr.entity.orangnize.HosDutyLevel;
import com.chd.hrp.hr.entity.orangnize.HosStation;

public interface HosDutyLevelMapper  extends SqlMapper{

	public List<HosDutyLevel> queryDutyLevelByCode(Map<String, Object> entityMap)  throws DataAccessException;

	public List<HosDutyLevel> queryDutyLevelByName(Map<String, Object> entityMap) throws DataAccessException;
	
	public void deleteBatchHrDutyLevel(List<HosDutyLevel> entityList)  throws DataAccessException;

	public List<HosDutyLevel> queryListDuty(List<HosDutyLevel> listVo);

	public List<Map<String, Object>> queryByCodeLevel(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDutyLevelByPrint(Map<String, Object> entityMap);

	
}
