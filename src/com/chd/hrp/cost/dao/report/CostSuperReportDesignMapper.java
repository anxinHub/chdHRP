package com.chd.hrp.cost.dao.report;

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
import com.chd.hrp.cost.entity.report.CostRepDefine;
import com.chd.hrp.cost.entity.report.CostRepDefineDict;
import com.chd.hrp.cost.entity.report.CostRepDefineEle;
import com.chd.hrp.cost.entity.report.CostRepDefinePara;

public interface CostSuperReportDesignMapper extends SqlMapper{

	//根据报表编码查询报表
	public CostRepDefine querySuperReportByCode(Map<String, Object> map) throws DataAccessException;
	
	//根据报表编码查询报表内容，大字段
	public CostRepDefine querySuperReportContentByCode(Map<String, Object> map) throws DataAccessException;
	
	//根据系统编码查询报表
	public List<CostRepDefine> querySuperReportByMod(Map<String, Object> map) throws DataAccessException;
	
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
	public List<CostRepDefineEle> querySuperReportEleByMod(Map<String, Object> map) throws DataAccessException;

	
	//根据报表元素查询报表元素参数
	public List<CostRepDefinePara> querySuperReportParaByEle(Map<String, Object> map) throws DataAccessException;
	
	//根据报表数据集查询报表数据集参数
	public List<CostRepDefinePara> querySuperReportParaByDs(Map<String, Object> map) throws DataAccessException;
	
	//根据字典编码查找SQL
	public CostRepDefineDict querySuperReportDictBySql(Map<String, Object> map) throws DataAccessException;
	
	//参数下拉框数据初始化，报表定义通用 
	public List<HrpAccSelect> querySuperReportParaSelectData(@Param("dict_sql") String dictSql) throws DataAccessException;
	
	
}
