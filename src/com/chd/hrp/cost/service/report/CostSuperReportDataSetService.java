package com.chd.hrp.cost.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CostSuperReportDataSetService {

	public String querySuperReportDsManager(Map<String, Object> map)throws DataAccessException;

	public String saveSuperReportDs(Map<String, Object> mapVo);

	public String deleteSuperReportDs(Map<String, Object> mapVo);	
	
	public int deleteSuperReportDsParaValues(Map<String, Object> mapVo);
	
	public List<Map<String, Object>> querySuperReportDSParaValues(Map<String, Object> mapVo);
	
	public String querySuperReportDSColoums(Map<String, Object> mapVo);
	
	public String saveSuperReportDSParaValues(Map<String, Object> map) throws DataAccessException ;

	public String querySuperReportDSParas(Map<String, Object> mapVo) throws DataAccessException;

	public String querySuperReportDsSql(Map<String, Object> mapVo) throws DataAccessException;
}
