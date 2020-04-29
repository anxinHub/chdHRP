/** 
* 2015-1-20 
* SelectDictService.java 
* author:pengjin
*/ 
package com.chd.hrp.hpm.service; 


import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AphiSelectDictService {

	//奖金项目
	public String queryItemAllDict(Map<String, Object> map) throws DataAccessException;
	
	public String queryAppModDict(Map<String, Object> map) throws DataAccessException;
	
	public String queryDeptKindDict(Map<String, Object> map) throws DataAccessException;
	
	public String queryDeptNatureDict(Map<String, Object> map) throws DataAccessException;
	
	public String queryDeptDict(Map<String, Object> map) throws DataAccessException;
	
	public String queryDeptDictByPerm(Map<String, Object> map) throws DataAccessException;
	
	public String queryDeptRefDict(Map<String, Object> map) throws DataAccessException;
	
	public String querySysDeptDict(Map<String, Object> map) throws DataAccessException;
	
	public String queryEmpDutyDict(Map<String, Object> map) throws DataAccessException;
	
	public String queryTargetNatureDict(Map<String, Object> map) throws DataAccessException;
	
	public String queryAphiIncomeItem(Map<String, Object> map) throws DataAccessException;
	
	public String queryAphiCostItem(Map<String, Object> map) throws DataAccessException;
	
	public String queryWorkItemSeq(Map<String,Object> entityMap) throws DataAccessException;
	
	// 指标取值方法参数表TARGET_METHOD_PARA
	public String queryTargetMethodPara(Map<String, Object> map) throws DataAccessException;

	// 表名:APHI_FORMULA 解释:奖金计算公式表
	public String queryFormula(Map<String, Object> map) throws DataAccessException;

	// 表名:APHI_FUN 解释:奖金函数表 
	public String queryFun(Map<String, Object> map) throws DataAccessException;
	
	// 表名:APHI_WORKITEM 解释:工作量指标表 
	public String queryWorkItem(Map<String, Object> map) throws DataAccessException;
	
	// 表名:APHI_TARGET 解释:奖金指标字典表  
	public String queryTarget(Map<String, Object> map) throws DataAccessException;
	
	// 表名:APHI_TARGET 解释:奖金指标字典表  带编码
	public String queryTargetCode(Map<String, Object> map) throws DataAccessException;

	// 表名:sys_comp 
	public String querySysComp(Map<String, Object> map) throws DataAccessException;

	// 表名:sys_copy
	public String querySysCopy(Map<String, Object> map) throws DataAccessException;
	
	public String queryEmpDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryIncomeItemSeq(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostItemSeq(Map<String,Object> entityMap) throws DataAccessException;
	
	public String querySchemeSeq(Map<String,Object> entityMap) throws DataAccessException;
	
	// 表名:APHI_FUN_TYPE 解释:奖金函数分类表
	public String queryHpmFunType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 表名:APHI_COM_TYPE 解释:奖金部件类型表
	public String queryHpmComType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 表名:APHI_COM_TYPE 解释:奖金部件类型表
	public String queryHpmFunParaMethod(Map<String, Object> entityMap) throws DataAccessException;
	
	// 表名:APHI_SUB_SCHEME_SEQ 解释:职工核算方案序列表
	public String querySubSchemeSeqDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//取指标 根据指标性质 取值方法 表aphi_target aphi_target_method
	public String queryTargetMethod(Map<String, Object> map) throws DataAccessException;
	
	//表名:APHI_COSTTYPE 解释:支出项目分类表
	public String queryCostTypeDict(Map<String, Object> map) throws DataAccessException;
	
	//解释:工作量项目
	public String queryWorkItemSeqMore(Map<String, Object> entityMap) throws DataAccessException;
	
	//解释:函数参数类型
	public String queryHpmFunParaType(Map<String, Object> entityMap) throws DataAccessException;
	
	//解释:科室性质
	public String queryHpmDeptNature(Map<String, Object> entityMap) throws DataAccessException;
	//解释:存储过程包名
	public String queryHpmOraclePkg(Map<String, Object> entityMap) throws DataAccessException;
	
	//解释:存储过程包名
	public String querySysGroupDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// 科室字典(传入时间)
	public String queryDeptDictTime(Map<String, Object> entityMap) throws DataAccessException;

	//工作量下拉框(时间)
	public String queryHpmWorkitemSeqTime(Map<String, Object> entityMap) throws DataAccessException;

	//收入项目下拉框(数据准备日期查询)
	public String queryIncomeItemSeqTime(Map<String, Object> entityMap)throws DataAccessException;

	//支出项目下拉框(带日期)
	public String queryCostItemSeqTime(Map<String, Object> entityMap)throws DataAccessException;


	//职工字典查询(编码)
	public String queryEmpDictByCode(Map<String, Object> entityMap)throws DataAccessException;

	
	//其它平台科室
	public String queryAphiDeptHip(Map<String, Object> entityMap)throws DataAccessException;
	
	//绩效科室
	public String queryAphiDeptDict(Map<String, Object> entityMap)throws DataAccessException;
	
	//奖金项目字典(职工)
	public String queryAphiEmpItem(Map<String, Object> entityMap)throws DataAccessException;
	
	//模板分类字典(职工)
	public String queryAphiTemplateKind(Map<String, Object> entityMap)throws DataAccessException;

}
