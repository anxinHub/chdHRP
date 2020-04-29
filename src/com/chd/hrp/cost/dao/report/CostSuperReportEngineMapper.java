package com.chd.hrp.cost.dao.report;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.report.RepDefine;
import com.chd.hrp.acc.entity.report.RepInstance;
import com.chd.hrp.cost.entity.report.CostRepDefine;
import com.chd.hrp.cost.entity.report.CostRepInstance;

public interface CostSuperReportEngineMapper extends SqlMapper{

		
	//根据系统编码查询报表
	public List<CostRepDefine> queryAccSuperReportByPerm(Map<String, Object> map) throws DataAccessException;

	//根据报表编码、年度、月份查询报表实例数据
	public CostRepInstance querySuperReportInstance(Map<String, Object> map) throws DataAccessException;
	
	//报表解析-执行函数、自定义SQL
	public List<String> querySuperReportInstanceByFun(@Param("fun_sql") String fun_sql) throws DataAccessException;
	
	//报表解析-执行存储过程
	public String querySuperReportInstanceByProc(Map<String, Object> map)throws DataAccessException;
	
	//删除报表实例数据
	public int deleteSuperReportInstance(Map<String, Object> map) throws DataAccessException;
	
	//添加报表实例数据
	public int insertSuperReportInstance(Map<String, Object> map) throws DataAccessException;
	
	//保存报表内容，大字段
	public int saveSuperReportContent(Map<String, Object> map) throws DataAccessException;
	//执行数据集查询
	public List<Map<String,Object>> querySql(String sql) throws DataAccessException;

}
