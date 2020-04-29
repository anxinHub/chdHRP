package com.chd.hrp.hr.dao.organize;


import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HosDuty;
import com.chd.hrp.hr.entity.orangnize.HosStation;

public interface HosDutyInfoMapper  extends SqlMapper{
	/**
	 * 查询
	 * @param entityMap
	 * @return
	 */
	public List<HosDuty> queryDutyByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询
	 * @param entityMap
	 * @return
	 */
	public List<HosDuty> queryDutyByName(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量删除
	 * @param entityList
	 */
	public void deleteBatchHrDuty(List<HosDuty> entityList) throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryHrDutyPrint(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryDutyCode(Map<String, Object> entityMap);
    
}
