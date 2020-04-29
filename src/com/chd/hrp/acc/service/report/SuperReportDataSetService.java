package com.chd.hrp.acc.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface SuperReportDataSetService {

	public String querySuperReportDsManager(Map<String, Object> map)throws DataAccessException;

	public String saveSuperReportDs(Map<String, Object> mapVo);

	public String deleteSuperReportDs(Map<String, Object> mapVo);	
	
	public int deleteSuperReportDsParaValues(Map<String, Object> mapVo);
	
	public List<Map<String, Object>> querySuperReportDSParaValues(Map<String, Object> mapVo);
	
	public String querySuperReportDSColoums(Map<String, Object> mapVo);
	
	public String saveSuperReportDSParaValues(Map<String, Object> map) throws DataAccessException ;

	public String querySuperReportDSParas(Map<String, Object> mapVo) throws DataAccessException;

	public String querySuperReportDsSql(Map<String, Object> mapVo) throws DataAccessException;
	
	public String saveReportQuery(Map<String, Object> map) throws DataAccessException;
	
}
