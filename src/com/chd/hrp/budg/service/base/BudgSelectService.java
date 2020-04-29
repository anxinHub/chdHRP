package com.chd.hrp.budg.service.base;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface BudgSelectService {
	/**
	 * 医保类型性质下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgYBTypeNature(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医保类型下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgYBType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据付费机制 查询 医保类型下拉框（查 医保付费机制维护表 BUDG_YB_PAY_MODE_SET）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgYBTypeByMode(Map<String, Object> entityMap)	throws DataAccessException;

	/**
	 * 病种名称下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgSingleDisease(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 病种名称下拉框 (根据医保类型编码 查询对应的 单病种)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgSingleDiseaseByInsCode(Map<String, Object> mapVo)throws DataAccessException;	
	
	//物资分类
	public String queryMatTypes(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 部门类型下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDeptType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 部门分类下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDeptKind(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 部门性质下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDeptNature(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 部门支出性质下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDeptOut(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 费用标准性质下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryBudgCharStanNature(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 指标性质
	 * @param mapVo
	 * @return
	 */
	public String queryBudgDataNature(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 费用标准下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryBudgChargeStan(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 费用标准下拉框  费用标准取值方法维护 添加、修改页面专用
	 * @param mapVo
	 * @return
	 */
	public String queryBudgChargeStanGetWay(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据费用标准 查询其对应科室 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgChargeStanDept(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 取值方法下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryBudgGetValueWay(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 计算公式下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryBudgFormula(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 取值函数下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryBudgFun(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 函数参数取值 下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryBudgFunParaMethod(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 函数参数 部件类型 下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryBudgComType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 存储过程包名 下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryBudgOraclePkg(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 收入科目级次下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgSubjLevel(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 支出科目级次下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgCostSubjLevel(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 科目性质下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgAccSubjNature(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算科目下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgSubj(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 *  会计科目下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccSubj(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 *  会计科目下拉框 支出项目字典页面 专用 勿动
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccSubjDict(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 收入预算科目下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIncomeSubj(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算年度下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgYear(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 预算年度下拉框(上下十年)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgYearTen(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 预算年度下拉框(启始年上五年)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgYearFive(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 收入预算科目类型下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIncomeSubjType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 支出预算科目类型下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgCostSubjType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据科目类型 查询收入预算科目 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIncomeTypeSet(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据科目类型 查询支出预算科目 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgCostTypeSet(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 科室 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDeptDict(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 基本指标名称下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDeptindex_code_name(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 基本指标名称下拉框  基本指标取值方法维护 添加、修改页面专用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIndexCodeGetWay(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 基本指标对应 预算科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgBasicIndexDept(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 指标性质下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public  String queryBudgIndexNature(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 函数类型下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String qureyBudgFunType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 元素类型下拉框（计算公式页面用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryElementType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 工资项目下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWageItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 期间属性 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgPeriodNature(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 奖金项目下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgAwardsItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 编制方法 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgEditMethod(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 取值方法（get_way）下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgGetWay(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 分解方法（resolve_way） 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgResolveWay(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 药品类别 下拉框带变更号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDrugType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 药品类别 下拉框 不带变更号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMedType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**  
	 * 固定资产类别 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgCostFassetType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 支出项目下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgPaymentItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 支出项目下拉框 带变更号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgPaymentItemDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 预算层次 budg_level （01医院年度 02医院月份 03科室年度 04科室月份 ） 
	 * 编制方法EDIT_METHOD（01零基预算 02增量预算 03确定预算 04概率预算 ） 从  业务预算编制方案表 查询 预算指标下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String qureyBudgIndexFromPlan(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 预算指标下拉框（查询表 BUDG_INDEX_DICT）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIndexDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 预算指标(可累加)下拉框（查询表 BUDG_INDEX_DICT）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIndexAccumulationDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 分解或汇总（resolve_or_sum） 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgResolveOrSum(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 预算层次（budg_level） 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgLevel(Map<String, Object> mapVo) throws DataAccessException;

	public String queryBudgCheckType(Map<String, Object> mapVo)throws  DataAccessException;

	public String queryBudgBcState(Map<String, Object> mapVo)throws DataAccessException;

	public String queryBudgState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**	
	 * 根据   编制方法EDIT_METHOD（01零基预算 02增量预算 03确定预算 04概率预算 ） 从  医院年度收入预算编制方案表 查询 科目下拉框
	 */
	public String qureySubjIndexFromPlan(Map<String, Object> mapVo)	throws DataAccessException;
	
	/**
	 * 根据指标查询科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgIndexDeptSet(Map<String, Object> mapVo) throws DataAccessException;
	/**	
	 * 根据
	 *   分解或汇总方法(RESOLVE_OR_SUM)从  医院年度收入预算编制方案表 查询 科目下拉框
	 */
	public String qureyResolveSubjIndexFromPlan(Map<String, Object> mapVo)	throws DataAccessException;

	
	/**	
	 * 根据  编制方法为分解方法 
			从  医院年度收入预算编制方案表 查询 分解 科目下拉框
	 */
	public String qureySubjCodeFromPlan(Map<String, Object> mapVo)	throws DataAccessException;
	/**
	 * 项目名称（proj_name） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryProjName(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 项目负责人（state） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryConEmpId(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 项目状态 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String qureyProjStateSelect(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 状态 下拉框（01 新建 02已提交 03 已审核）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgApplyState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 预算申报单号 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgApplyCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 申报类型 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgApplyType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 资金来源 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgSource(Map<String, Object> mapVo) throws DataAccessException;

	public String qureyProjSetUpStateSelect(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据项目状态       查询项目下拉框 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryProjByState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 项目类型 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgProjType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 项目级别 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgProjLevel(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医院下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosInfoDict(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 资金来源 下拉框 不包括 自筹资金
	 * @param mapVo
	 * @return
	 */
	public String queryBudgSourceNotExistsZCZJ(Map<String, Object> mapVo);

	
	/**
	 * 支出项目 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPaymentItem(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资金计划状态 下拉框（01 新建 02 审核）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String qureyCashPlanStateSelect(Map<String, Object> mapVo)  throws DataAccessException;

	/**
	 * 资产性质 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssNatures(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产字典 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产字典 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssDictInassets(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 业务 调整单号 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgAdjustCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 现金流量项目 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCashItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 现金流量类型 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCashType(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 科室下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgHosDept(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 预算月份  下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	public String queryBudgMonth(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 资金流向   下拉框
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	public String queryCashDire(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 部门下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryDept(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 物资分类与预算科目对应关系表中查询物资分类
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgMatTypeSubj(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 系统字典表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgSysDict(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 职工
	 * @param mapVo (f_code required=true)
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryEmpDict(Map<String, Object> mapVo);
	/**
	 * 职工类别
	 * @param mapVo (f_code required=true)
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryEmpType(Map<String, Object> mapVo);
	/**
	 * 全部科室下拉框包括已停用  
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryBudgDeptDictAll(Map<String, Object> mapVo);
	/**
	 * 所有奖金项目包括已停用 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryBudgAwardsItemAll(Map<String, Object> mapVo);
	/**
	 * 所有职工类别 包括已停用
	 * @param mapVo (f_code required=true)
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryEmpTypeAll(Map<String, Object> mapVo);
	
	/**
	 * 职工类别下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryBudgEmpType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 职工名称下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryBudgEmpName(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 计划类型下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryBudgPlanType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 *  科室 下拉框 (其他费用预算使用   dept_no )  预算科室（IS_BUDG=1）
	 * @param mapVo
	 * @return
	 */
	public String queryBudgDept(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医疗收入 调整单号  下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryIncomeAdjustCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 其他收入 调整单号  下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryElseIncomeAdjustCode(Map<String, Object> entityMap)	throws DataAccessException;

	public String queryAssNaturesInassets(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 采购资产性质下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryAssNaturesBuget(Map<String, Object> mapVo) throws DataAccessException;	
	/**
	 * 仓库  下拉框 不带NO
	 * @param mapVo
	 * @return
	 */
	public String queryHosStoreDict(Map<String, Object> mapVo) throws DataAccessException;	
	/**
	 * 仓库  下拉框 带NO
	 * @param mapVo
	 * @return
	 */
	public String queryHosStoreDictNo(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 自定义分解系数下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgResolveDataDict(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 收入预算科目类别
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgTypeCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 支出预算科目类别
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgCostTypeCode(Map<String, Object> mapVo) throws DataAccessException;

	public String queryBudgCPlan(Map<String, Object> mapVo) throws DataAccessException;
	 /**
     * 查询控制层次
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	public String queryContLSelect(Map<String, Object> mapVo)throws DataAccessException;
	 /**
     * 查询控制期间
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	public String queryContPSelect(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询控制方式
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	public String queryContWSelect(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询预算表
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	public String queryTabCode(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询控制模式
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	public String queryContMSelect(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询关联节点
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	public String queryReLinkSelect(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 查询控制节点
    * @param mapVo
    * @return
    * @throws DataAccessException
    */
	public String queryContNoteSelect(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 查询占用状态
    * @param mapVo
    * @return
    * @throws DataAccessException
    */
    public String queryUseStateSelect(Map<String, Object> mapVo)throws DataAccessException;

	
}
