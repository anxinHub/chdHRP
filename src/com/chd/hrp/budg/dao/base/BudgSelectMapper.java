package com.chd.hrp.budg.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface BudgSelectMapper extends SqlMapper {
	/**
	 * 医保类型性质下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgYBTypeNature(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;

	/**
	 * 医保类型下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgYBType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据付费机制 查询 医保类型下拉框（查 医保付费机制维护表 BUDG_YB_PAY_MODE_SET）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryBudgYBTypeByMode(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;;
	/**
	 * 部门类型下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptType(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	//物资分类
	public List<Map<String,Object>> queryMatTypes(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 病种名称下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgSingleDisease(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	/**
	 * 病种名称下拉框 (根据医保类型编码 查询对应的 单病种)
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgSingleDiseaseByInsCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;	
 
	/**
	 * 部门分类下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptKind(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	/**
	 * 部门性质下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptNature(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 部门支出性质下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptOut(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 费用标准性质下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgCharStanNature(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return数据性质
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDataNature(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 费用标准下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgChargeStan(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 费用标准下拉框  费用标准取值方法维护 添加、修改页面专用
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgChargeStanGetWay(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据费用标准 查询其对应科室 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgChargeStanDept(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 取值方法下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgGetValueWay(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 计算供公式下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgFormula(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 取值函数下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgFun(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 函数参数取值 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgFunParaMethod(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 函数参数 部件类型 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgComType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 存储过程包名 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgOraclePkg(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 收入科目级次下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgSubjLevel(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 支出科目级次下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgCostSubjLevel(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * 科目性质下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgAccSubjNature(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	/**
	 * 预算科目下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgSubj(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 会计科目下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccSubj(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 会计科目下拉框  支出项目字典页面 专用 勿动
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAccSubjDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 收入预算科目下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgIncomeSubj(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	/**
	 * 预算年度下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgYear(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 预算年度下拉框(系统年上下十年)
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	
	public List<Map<String, Object>> queryBudgYearTen(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 预算年度下拉框(启始年上五年)
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	
	public List<Map<String, Object>> queryBudgYearFive(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 收入预算科目类型下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	
	public List<Map<String, Object>> queryBudgIncomeSubjType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 支出预算科目类型下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgCostSubjType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据科目类型 查询收入预算科目 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgIncomeTypeSet(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据科目类型 查询支出预算科目 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgCostTypeSet(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 科室 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptDict(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 基本指标对应 预算科室下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgBasicIndexDept(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 基本指标名称下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgDeptindex_code_name(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 基本指标名称下拉框  基本指标取值方法维护 添加、修改页面专用
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgIndexCodeGetWay(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 指标性质下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgIndexNature(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 函数分类下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> qureyBudgFunType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 元素类型下拉框（计算公式页面用）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryElementType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 工资项目 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgWageItem(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 期间属性 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgPeriodNature(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 奖金项目 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgAwardsItem(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 编制方法 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgEditMethod(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 取值方法 （get_way） 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgGetWay(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 分解方法（resolve_way） 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgResolveWay(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 药品类别 下拉框 带变更号
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgDrugType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 药品类别 下拉框 不带变更号
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgMedType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 固定资产类别 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgCostFassetType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 支出项目
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgPaymentItem(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 支出项目下拉框 带变更号
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgPaymentItemDict(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据 预算层次 budg_level （01医院年度 02医院月份 03科室年度 04科室月份 ） 
	 * 编制方法EDIT_METHOD（01零基预算 02增量预算 03确定预算 04概率预算 ） 从  业务预算编制方案表 查询 指标下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> qureyBudgIndexFromPlan(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据 
	 * 编制方法EDIT_METHOD（01零基预算 02增量预算 03确定预算 04概率预算 ） 从  医院年度收入预算编制方案表 查询 科目下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> qureySubjIndexFromPlan(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	
	
	/**
	 * 预算指标下拉框（查询表 BUDG_INDEX_DICT）
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgIndexDict(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 预算指标(可累加)下拉框（查询表 BUDG_INDEX_DICT）
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgIndexAccumulationDict(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 分解或汇总（resolve_or_sum） 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgResolveOrSum(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 预算层次（budg_level） 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgLevel(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 审批类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryBudgCheckType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 审批状态下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryBudgBcState(Map<String, Object> mapVo, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String,Object>> queryBudgState(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据指标查询科室下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgIndexDeptSet(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**	
	 * 根据
	 *   分解或汇总方法(RESOLVE_OR_SUM)从  医院年度收入预算编制方案表 查询 科目下拉框
	 */
	public List<Map<String,Object>> qureyResolveSubjIndexFromPlan(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;

	
	
	
	/**
	 * 根据  编制方法为分解方法 
			从  医院年度收入预算编制方案表 查询 分解 科目下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> qureySubjCodeFromPlan(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 项目名称（proj_name） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryProjName(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 项目负责人（state） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryConEmpId(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 项目状态 下拉框
	 * @param mapVo
	 * @return
	 */
	public List<Map<String,Object>> qureyProjStateSelect(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 状态 下拉框（01 新建 02已提交 03 已审核）
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String,Object>> queryBudgApplyState(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 预算申报单号 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgApplyCode(Map<String, Object> mapVo,	RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 申报类型 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgApplyType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 资金来源 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String,Object>> queryBudgSource(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 立项时项目状态下拉菜单 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> qureyProjSetUpStateSelect(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据项目状态       查询项目下拉框 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryProjByState(Map<String, Object> mapVo,	RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 项目类型 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgProjType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 项目级别 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgProjLevel(Map<String, Object> entityMap,	RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 医院下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryHosInfoDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 *资金来源 下拉框 不包括 自筹资金 
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String,Object>> queryBudgSourceNotExistsZCZJ(Map<String, Object> mapVo, RowBounds rowBounds);

	/**
	 * 支出项目下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryPaymentItem(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 资金计划状态 下拉框（01 新建 02 审核）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> qureyCashPlanStateSelect(Map<String, Object> mapVo)  throws DataAccessException;

	/**
	 * 资产性质 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssNatures(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 资产字典下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 资产字典下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssDictInassets(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 业务 调整单号下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgAdjustCode(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 现金流量项目 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryCashItem(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	/**
	 * 现金流量类型 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryCashType(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	/**
	 * 科室下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgHosDept(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 预算月份 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgMonth(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 现金流向 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryCashDire(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 部门下拉框 不分页
	 * @param mapVo
	 * @return
	 */
	public List<Map<String,Object>> queryDept(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 科室 下拉框 (其他费用预算使用   dept_no ) 预算科室(IS_BUDG=1)
	 * @param mapVo
	 * @return
	 */
	public List<Map<String,Object>> queryBudgDept(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 物资分类与预算科目对应关系表中查询物资分类
	 * @param mapVo
	 * @return
	 */
	public List<Map<String,Object>> queryBudgMatTypeSubj(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 
	 * 系统字典表
	 * @param mapVo
	 * @return
	 */
	public List<Map<String,Object>> queryBudgSysDict(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 职工类别下拉框
	 */
	public List<Map<String,Object>> queryBudgEmpType(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 职工名称下拉框
	 * @param rowBounds 
	 */
	public List<Map<String,Object>> queryBudgEmpName(Map<String, Object> mapVo, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 工资项目下拉框
	 */
	public List<Map<String,Object>> queryBudgWageItem(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 职工
	 * @param mapVo (f_code required=true)
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryEmpDict(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryEmpType(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 全部科室下拉框包括已停用  
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryBudgDeptDictAll(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 所有奖金项目包括已停用 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryBudgAwardsItemAll(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 所有职工类别 包括已停用
	 * @param mapVo (f_code required=true)
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> queryEmpTypeAll(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 *计划类型下拉框
	 */
	public List<Map<String,Object>> queryBudgPlanType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医疗收入  调整单号 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryIncomeAdjustCode(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 其他收入  调整单号 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryElseIncomeAdjustCode(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;

	public List<Map<String,Object>> queryAssNaturesInassets(Map<String, Object> entityMap,
			RowBounds rowBounds);
	/**
	 * 采购资产性质 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String,Object>> queryAssNaturesBuget(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 仓库  下拉框 不带NO
	 * @param mapVo
	 * @return
	 */
	public List<Map<String,Object>> queryHosStoreDict(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;	
	/**
	 * 仓库  下拉框 带NO
	 * @param mapVo
	 * @return
	 */
	public List<Map<String,Object>> queryHosStoreDictNo(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 自自定义分解系数下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>>  queryBudgResolveDataDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 收入预算科目类别
	 * @param mapVo
	 * @return
	 */
	public List<Map<String,Object>>  queryBudgTypeCode(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 支出预算科目类别
	 * @param mapVo
	 * @return
	 */
	public List<Map<String,Object>>  queryBudgCostTypeCode(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryBudgCPlan(	Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryContLSelect(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryContPSelect(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryContWSelect(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryTabCode(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryContMSelect(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryReLinkSelect(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryContNoteSelect(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String,Object>> queryUseStateSelect(Map<String, Object> entityMap)throws DataAccessException;



	
}
