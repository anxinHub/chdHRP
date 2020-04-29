/** 
* 2015-1-20 
* SystemSelectDictMapper.java 
* author:pengjin
*/ 
package com.chd.hrp.hpm.dao; 

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AphiSelectDictMapper  extends SqlMapper{

	//奖金项目下拉框
	public List<Map<String, Object>> queryItemAllDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAppModDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryDeptKindDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryDeptNatureDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryDeptDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryDeptDictByPerm(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryDeptRefDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> querySysDeptDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryEmpDutyDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryEmpDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryTargetNatureDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAphiIncomeItem(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAphiCostItem(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryIncomeItemSeq(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostItemSeq(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryWorkItemSeq(Map<String,Object> entityMap) throws DataAccessException;
	
	// 指标取值方法参数表TARGET_METHOD_PARA
	public List<Map<String, Object>> queryTargetMethodPara(Map<String,Object> entityMap) throws DataAccessException;

	// 表名:APHI_FORMULA 解释:奖金计算公式表
	public List<Map<String, Object>> queryFormula(Map<String, Object> entityMap) throws DataAccessException;

	// 表名:APHI_FUN 解释:奖金函数表 
	public List<Map<String, Object>> queryFun(Map<String, Object> entityMap) throws DataAccessException;

	// 表名:APHI_FUN 解释:奖金函数表
	public List<Map<String, Object>> queryWorkItem(Map<String, Object> entityMap) throws DataAccessException;

	// 表名:APHI_TARGET 解释:奖金指标字典表  
	public List<Map<String, Object>> queryTarget(Map<String, Object> entityMap) throws DataAccessException;
	
	//表名:APHI_TARGET 解释:奖金指标字典表  带编码
	public List<Map<String, Object>> queryTargetCode(Map<String, Object> entityMap) throws DataAccessException;

	// 表名:sys_comp
	public List<Map<String, Object>> querySysComp(Map<String, Object> entityMap) throws DataAccessException;

	// 表名:sys_copy
	public List<Map<String, Object>> querySysCopy(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public List<Map<String, Object>> querySchemeSeq(Map<String, Object> entityMap) throws DataAccessException;
	
	// 表名:APHI_FUN_TYPE 解释:奖金函数分类表
	public List<Map<String, Object>> queryHpmFunType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 表名:APHI_FUN_TYPE 解释:奖金函数分类表
	public List<Map<String, Object>> queryHpmComType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 表名:APHI_FUN_TYPE 解释:奖金函数分类表
	public List<Map<String, Object>> queryHpmFunParaMethod(Map<String, Object> entityMap) throws DataAccessException;
		
	//表名:APHI_SUB_SCHEME_SEQ 解释:职工核算方案序列表
	public List<Map<String, Object>> querySubSchemeSeqDict(Map<String,Object> entityMap) throws DataAccessException;
	
	//取指标 根据指标性质 取值方法 表aphi_target aphi_target_method
	public List<Map<String, Object>> queryTargetMethod(Map<String, Object> entityMap) throws DataAccessException;
	
	//表名:APHI_COSTTYPE 解释:支出项目分类表
	public List<Map<String, Object>> queryCostTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//表名:APHI_COSTTYPE 解释:工作量项目
	public List<Map<String, Object>> queryWorkItemSeqMore(Map<String, Object> entityMap) throws DataAccessException;
	
	//表名:aphi_com_type 解释:查询 函数参数类型
	public List<Map<String, Object>> queryHpmFunParaType(Map<String, Object> entityMap) throws DataAccessException;
	
	//表名:aphi_dept_nature 解释:查询 科室性质
	public List<Map<String, Object>> queryHpmDeptNature(Map<String, Object> entityMap) throws DataAccessException;
	
	//表名:user_procedures 解释:查询 存储过程包名
	public List<Map<String, Object>> queryHpmOraclePkg(Map<String, Object> entityMap) throws DataAccessException;
	
	//表名:SYS_GROUP_DICT 解释:查询 集团
	public List<Map<String, Object>> querySysGroupDict(Map<String, Object> entityMap) throws DataAccessException;

	//科室字典(传入时间)
	public List<Map<String, Object>> queryDeptDictTime(Map<String, Object> entityMap) throws DataAccessException;

	//表名:APHI_COSTTYPE 解释:工作量项目(传入时间)
	public List<Map<String, Object>> queryHpmWorkitemSeqTime(Map<String, Object> entityMap)throws DataAccessException;

	//收入项目下拉框(数据准备日期查询)
	public List<Map<String, Object>> queryIncomeItemSeqTime(Map<String, Object> entityMap)throws DataAccessException;

	//支出项目下拉框(带日期)
	public List<Map<String, Object>> queryCostItemSeqTime(Map<String, Object> entityMap) throws DataAccessException;


	//职工字典查编码
	public List<Map<String, Object>> queryEmpDictByCode(Map<String, Object> entityMap)throws DataAccessException;

	
	//其它平台科室
	public List<Map<String, Object>> queryAphiDeptHip(Map<String, Object> entityMap) throws DataAccessException;
	
	//绩效科室
	public List<Map<String, Object>> queryAphiDeptDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//奖金项目字典(职工)
	public List<Map<String, Object>> queryAphiEmpItem(Map<String, Object> entityMap) throws DataAccessException;
	
	//模板分类字典
	public List<Map<String, Object>> queryAphiTemplateKind(Map<String, Object> entityMap) throws DataAccessException;
	
}

