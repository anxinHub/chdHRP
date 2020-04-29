package com.chd.hrp.hr.service.organize;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HosDutyLevel;

public interface HosDutyLevelService {

	String addHrDutyLevel(Map<String, Object> mapVo) throws DataAccessException;

	HosDutyLevel queryByCode(Map<String, Object> mapVo) throws DataAccessException;

	String updateHrDutyLevel(Map<String, Object> mapVo) throws DataAccessException;

	String deleteHrDutyLevel(List<HosDutyLevel> listVo) throws DataAccessException;

	String queryHrDutyLevel(Map<String, Object> page) throws DataAccessException;

	String importDate(Map<String, Object> mapVo) throws DataAccessException;

	List<HosDutyLevel> queryListDuty(List<HosDutyLevel> listVo) throws DataAccessException;
	/**
	 * 打印
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
List<Map<String,Object>> queryDutyLevelByPrint(Map<String, Object> page)throws DataAccessException;

}
