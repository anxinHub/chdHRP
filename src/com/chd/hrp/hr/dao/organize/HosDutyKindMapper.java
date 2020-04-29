package com.chd.hrp.hr.dao.organize;


import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HosDutyKind;

public interface HosDutyKindMapper  extends SqlMapper{
	/**
	 * 批量删除
	 * @param entityList
	 */
	public void  deleteBatchHrStationKind(List<HosDutyKind> entityList) throws DataAccessException;

	public List<HosDutyKind> queryStationKindByCode(Map<String, Object> entityMap) throws DataAccessException;

	public List<HosDutyKind> queryStationKindByName(Map<String, Object> entityMap) throws DataAccessException;

	public List<HosDutyKind> queryListDutyKind(List<HosDutyKind> listVo);

	public List<Map<String, Object>> queryByCodeKind(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDutyKindByPrint(Map<String, Object> entityMap);
    
}
