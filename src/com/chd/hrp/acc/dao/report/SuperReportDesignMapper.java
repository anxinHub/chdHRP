package com.chd.hrp.acc.dao.report;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.acc.entity.report.RepDefine;
import com.chd.hrp.acc.entity.report.RepDefineDict;
import com.chd.hrp.acc.entity.report.RepDefineEle;
import com.chd.hrp.acc.entity.report.RepDefinePara;

public interface SuperReportDesignMapper extends SqlMapper{

	//根据报表编码查询报表
	public RepDefine querySuperReportByCode(Map<String, Object> map) throws DataAccessException;
	
	//根据报表编码查询报表内容，大字段
	public RepDefine querySuperReportContentByCode(Map<String, Object> map) throws DataAccessException;
	
	//根据系统编码查询报表
	public List<RepDefine> querySuperReportByMod(Map<String, Object> map) throws DataAccessException;
	
	//保存报表-添加
	public int insertSuperReport(Map<String, Object> map) throws DataAccessException;
	
	//保存报表-修改
	public int updateSuperReport(Map<String, Object> map) throws DataAccessException;
	
	//保存报表内容，大字段
	public int updateSuperReportContent(Map<String, Object> map) throws DataAccessException;
	
	//删除判断：根据模块编码、报表编码查询报表实例
	public int querySuperReportInstanceCountByCode(Map<String, Object> map) throws DataAccessException;
	
	//根据报表编码删除报表
	public int deleteSuperReportByCode(Map<String, Object> map) throws DataAccessException;
	
	
	//根据系统编码查询报表元素
	public List<RepDefineEle> querySuperReportEleByMod(Map<String, Object> map) throws DataAccessException;

	
	//根据报表元素查询报表元素参数
	public List<RepDefinePara> querySuperReportParaByEle(Map<String, Object> map) throws DataAccessException;
	
	//根据报表数据集查询报表数据集参数
	public List<RepDefinePara> querySuperReportParaByDs(Map<String, Object> map) throws DataAccessException;
	
	//根据字典编码查找SQL
	public RepDefineDict querySuperReportDictBySql(Map<String, Object> map) throws DataAccessException;
	
	//参数下拉框数据初始化，报表定义通用 
	public List<HrpAccSelect> querySuperReportParaSelectData(@Param("dict_sql") String dictSql) throws DataAccessException;
	

}
