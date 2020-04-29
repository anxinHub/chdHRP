package com.chd.hrp.acc.dao.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface SuperReportDataSetMapper extends SqlMapper {

	// 根据系统编码查询报表元素
	public List<Map<String, Object>> querySuperReportDsByMod(Map<String, Object> map) throws DataAccessException;

	public List<Map<String, Object>> querySuperReportDsByDSCode(Map<String, Object> map) throws DataAccessException;

	public List<Map<String, Object>> querySuperReportDSColoums(Map<String, Object> mapVo);

	public List<Map<String, Object>> querySuperReportDsColForMake(Map<String, Object> mapVo);

	public void saveSuperReportDs(Map<String, Object> mapVo);

	public void deleteSuperReportDs(Map<String, Object> mapVo);

	public void saveSuperReportDSColoums(Map<String, Object> mapVo);

	public void deleteSuperReportDSColoums(Map<String, Object> mapVo);

	public int deleteSuperReportDsParaValues(Map<String, Object> mapVo);
	// 保存到参数赋值表中
	public void saveRepDSParam(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryDetailParamForMakeReport(Map<String, Object> mapVo);

	// 更新参数
	public void updateRepRepDSPara(List<Map<String, Object>> listVo);

	public void deleteRepDSPara(Map<String, Object> mapVo);

	public void saveRepRepDSPara(Map<String, Object> map);

	public List<Map<String, Object>> queryDetailParamForMakeReport2(Map<String, Object> mapVo);
	
	public  List<Map<String, Object>> queryDsSql(Map<String, Object> mapVo);
	
	public List<Map<String, Object>> querySql(String sql);
	
	//获取参数
	public List<Map<String, Object>> querySuperReportDSParas(Map<String, Object> mapVo) throws DataAccessException;
	
	//获取查询sql
	public String querySuperReportDsSql(Map<String, Object> map) throws DataAccessException;
	
	public int saveSuperReportDSParaValues(Map<String, Object> mapVo);
	
	public int saveReportQuery(Map<String, Object> mapVo);
	
	public List<Map<String, Object>> querySuperReportDSparaValue(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String, Object>> queryDsParaByDistinct(Map<String, Object> mapVo) throws DataAccessException;

	public int deleteReportQuery(Map<String, Object> map)throws DataAccessException;
	
	//查询报表条件设置
	public List<Map<String, Object>> queryRepQuery(Map<String, Object> mapVo) throws DataAccessException;
	
}
