package com.chd.hrp.prm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.prm.entity.HrpPrmSelect;

public interface HrpPrmSelectMapper extends SqlMapper {
	// 科室分类
	public List<HrpPrmSelect> queryPrmDeptKind(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 科室性质
	public List<HrpPrmSelect> queryPrmDeptNature(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 科室字典
	public List<HrpPrmSelect> queryPrmDept(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 科室变更字典
	public List<HrpPrmSelect> queryPrmDeptDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	
	// KPI指标维护
	public List<HrpPrmSelect> queryPrmKpiDim(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// KIP指标库

	public List<HrpPrmSelect> queryPrmKpiLibDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/* KPI指标性质字典表 */
	public List<HrpPrmSelect> queryPrmKpiNatureDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/* 科室平台对应性质 */
	public List<HrpPrmSelect> queryPrmDeptRefDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/* 取值方法 */
	public List<HrpPrmSelect> queryPrmTargetMethodPara(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/* 指标性质 */
	public List<HrpPrmSelect> queryPrmTargetNature(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/* 绩效指标字典 */

	public List<HrpPrmSelect> quertPrmTargetDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/* 绩效指标性质 */

	public List<HrpPrmSelect> quertPrmTargetNatureDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/* 指示灯编码 */

	public List<HrpPrmSelect> quertPrmLedDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/* 函数分类 */

	public List<HrpPrmSelect> queryPrmFunType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/* 系统医院 */
	public List<HrpPrmSelect> quertSysHosInfoDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/* 目标管理 */
	public List<HrpPrmSelect> quertPrmGoalDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/* 计算公式 */
	public List<HrpPrmSelect> queryPrmFormula(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/* 绩效函数参数取值 */
	public List<HrpPrmSelect> queryPrmFunParaMethod(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	
	/* 计量单位 */
	public List<HrpPrmSelect> quertHosUnitDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/* 人员变更 */
	public List<HrpPrmSelect> quertPrmEmpDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/* 表名:PRM_GRADE_PARA 解释:0204 指标评分方法参数表  */
	public List<HrpPrmSelect> queryPrmGradePara(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/* 职务字典 */
	public List<HrpPrmSelect> queryPrmEmpDuty(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/* 职工字典 */
	public List<HrpPrmSelect> queryPrmEmp(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<HrpPrmSelect> queryHosEmpDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/* 职工变动表 */
	public List<HrpPrmSelect> queryPrmEmpDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/* 院级KPI指标编码 */
	public List<HrpPrmSelect> queryPrmHosKpi(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/* 科室KPI指标编码 */
	public List<HrpPrmSelect> queryPrmDeptKpi(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/* 职工KPI指标编码 */
	public List<HrpPrmSelect> queryPrmEmpKpi(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/*医院KPI指标上级编码*/
	public List<HrpPrmSelect> queryPrmHosKpiSuperKpiCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/*科室KPI指标上级编码*/
	public List<HrpPrmSelect> queryPrmDeptKpiSuperKpiCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/*职工KPI指标上级编码*/
	public List<HrpPrmSelect> queryPrmEmpKpiSuperKpiCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HrpPrmSelect> quertPrmComType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HrpPrmSelect> queryPrmOraclePkg(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	//同步平台分类
	public List<HrpPrmSelect> quneryPlatformKind(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryPrmDeptHip1(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryPrmDeptHipName(Map<String, Object> entityMap) throws DataAccessException;
}
