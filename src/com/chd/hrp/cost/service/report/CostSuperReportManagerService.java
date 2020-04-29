package com.chd.hrp.cost.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CostSuperReportManagerService {

	//报表管理页面，查询报表的所有实例
	public String querySuperReportInstanceList(Map<String,Object> map) throws DataAccessException;
	
	//报表管理页面，删除报表实例
	public String deleteBatchSuperReportInstance(Map<String,Object> map) throws DataAccessException;
	
	//报表字典页面，查询所有字典
	public String querySuperReportDictList(Map<String,Object> map) throws DataAccessException;
	
	//报表字典页面，保存报表字典
	public String saveSuperReportDict(Map<String,Object> map) throws DataAccessException;
	
	//报表字典页面，删除报表字典
	public String deleteBatchSuperReportDict(Map<String,Object> map) throws DataAccessException;
	
	//报表字典页面，根据字典编码查询报表字典
	public String querySuperReportDictByCode(Map<String,Object> map) throws DataAccessException;
	
	//报表元素页面，查询报表元素
	public String querySuperReportEleManager(Map<String,Object> map) throws DataAccessException;
	
	//报表元素页面，保存报表元素、报表参数
	public String saveSuperReportEle(Map<String,Object> map) throws DataAccessException;
	
	//报表元素页面，删除报表元素、报表参数
	public String deleteSuperReportEle(Map<String,Object> map) throws DataAccessException;
	
	//查询存储过程、函数(user_source)、视图(user_views)
	public String querySuperReportSourceAndViews(Map<String,Object> map) throws DataAccessException;
	
	//重新加载报表存储过程
	public String initSuperReportProc(Map<String,Object> map) throws DataAccessException;
}

