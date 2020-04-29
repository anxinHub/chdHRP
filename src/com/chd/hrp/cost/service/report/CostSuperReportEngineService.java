package com.chd.hrp.cost.service.report;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CostSuperReportEngineService {

	//根据系统编码查询报表
	public String queryAccSuperReportByPerm(Map<String,Object> map) throws DataAccessException;
	
	//根据报表编码查询报表实例数据
	public String querySuperReportInstance(Map<String,Object> map) throws DataAccessException;
	
	//根据报表模板生成报表实例数据
	public String saveSuperReportInstance(Map<String,Object> map) throws DataAccessException;
	
	//保存计算完后的报表内容，为了取报表单元格的数据
	public String saveSuperReportContent(Map<String,Object> map) throws DataAccessException;
	
	
}
