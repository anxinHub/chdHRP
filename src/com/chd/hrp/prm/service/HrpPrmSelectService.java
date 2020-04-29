package com.chd.hrp.prm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrpPrmSelectService {
	// 科室类别
	public String queryPrmDeptKind(Map<String, Object> entityMap) throws DataAccessException;
	
	//同步平台分类
	public String quneryPlatformKind(Map<String, Object> entityMap) throws DataAccessException;
	
	// 科室性质
	public String queryPrmDeptNature(Map<String, Object> entityMap) throws DataAccessException;

	// 科室字典
	public String queryPrmDept(Map<String, Object> entityMap) throws DataAccessException;

	// 科室变更字典
	public String queryPrmDeptDict(Map<String, Object> entityMap) throws DataAccessException;

	// KIP指标维度

	public String queryPrmKpiDim(Map<String, Object> entityMap) throws DataAccessException;

	/* KPI指标库 */

	public String queryPrmKpiLibDict(Map<String, Object> entityMap) throws DataAccessException;

	/* KPI指标性质字典表 */
	public String queryPrmKpiNatureDict(Map<String, Object> entityMap) throws DataAccessException;

	/* 科室平台对应性质 */
	public String queryPrmDeptRefDict(Map<String, Object> entityMap) throws DataAccessException;

	/* 取值方法 */
	public String queryPrmTargetMethodPara(Map<String, Object> entityMap) throws DataAccessException;

	/* 指标性质 */
	public String queryPrmTargetNature(Map<String, Object> entityMap) throws DataAccessException;

	/* 绩效指标字典 */
	public String quertPrmTargetDict(Map<String, Object> entityMap) throws DataAccessException;

	/* 绩效指标性质 */
	public String quertPrmTargetNatureDict(Map<String, Object> entityMap) throws DataAccessException;

	/* 指示灯编码 */
	public String quertPrmLedDict(Map<String, Object> entityMap) throws DataAccessException;

	/* 函数分类 */
	public String queryPrmFunType(Map<String, Object> entityMap) throws DataAccessException;

	/* 系统医院 */
	public String quertSysHosInfoDict(Map<String, Object> entityMap) throws DataAccessException;

	/* 目标管理 */
	public String quertPrmGoalDict(Map<String, Object> entityMap) throws DataAccessException;

	/* 计算公式 */
	public String queryPrmFormula(Map<String, Object> entityMap) throws DataAccessException;

	/* 绩效函数参数取值 */
	public String queryPrmFunParaMethod(Map<String, Object> entityMap) throws DataAccessException;

	/* 计量单位 */
	public String quertHosUnitDict(Map<String, Object> entityMap) throws DataAccessException;

	/* 人员变更表 */
	public String quertPrmEmpDict(Map<String, Object> entityMap) throws DataAccessException;

	/* 表名:PRM_GRADE_PARA 解释:0204 指标评分方法参数表 */
	public String queryPrmGradePara(Map<String, Object> entityMap) throws DataAccessException;
	
	/* 职务字典*/
	public String queryPrmEmpDuty(Map<String, Object> entityMap) throws DataAccessException;
	
	/* 职工字典*/
	public String queryPrmEmp(Map<String, Object> entityMap) throws DataAccessException;
	public String queryHosEmpDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/* 职工变动表 */
	public String queryPrmEmpDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/* 院级KPI指标编码 */
	public String queryPrmHosKpi(Map<String, Object> entityMap) throws DataAccessException;
	
	/* 科室KPI指标编码 */
	public String queryPrmDeptKpi(Map<String, Object> entityMap) throws DataAccessException;
	
	/* 职工KPI指标编码 */
	public String queryPrmEmpKpi(Map<String, Object> entityMap) throws DataAccessException;
	
	/* 医院KPI指标编码 */
	public String queryPrmHosKpiSuperKpiCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/* 科室KPI指标编码 */
	public String queryPrmDeptKpiSuperKpiCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/* 职工KPI指标编码 */
	public String queryPrmEmpKpiSuperKpiCode(Map<String, Object> entityMap) throws DataAccessException;
	
	public String quertPrmComType(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryPrmOraclePkg(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryPrmDeptHip1(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryPrmDeptHipName(Map<String, Object> entityMap)throws DataAccessException;
}
