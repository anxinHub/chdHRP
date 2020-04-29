package com.chd.hrp.acc.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;
 
public interface HrpAccSelectService {
	//五性分析指标类别
	public String queryAccWxType(Map<String, Object> entityMap) throws DataAccessException;
	
	//基本分析指标类别
	public String queryAccBasType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 科目类别
	public String querySubjType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 科目类别 
	public String querySubjTypeKind(Map<String, Object> entityMap) throws DataAccessException;

	// 科目性质
	public String querySubjNature(Map<String, Object> entityMap) throws DataAccessException;

	// 凭证类型
	public String queryVouchType(Map<String, Object> entityMap) throws DataAccessException;

	// 币种
	public String queryCur(Map<String, Object> entityMap) throws DataAccessException;

	// 核算类型
	public String queryCheckType(Map<String, Object> entityMap) throws DataAccessException;

	// 支出功能分类
	public String queryFunType(Map<String, Object> entityMap) throws DataAccessException;

	// 支出经济分类
	public String queryEcoType(Map<String, Object> entityMap) throws DataAccessException;

	// 现金流量类别
	public String queryCashType(Map<String, Object> entityMap) throws DataAccessException;

	// 部门类型
	public String queryDeptType(Map<String, Object> entityMap) throws DataAccessException;

	// 部门性质
	public String queryDeptNatur(Map<String, Object> entityMap) throws DataAccessException;

	// 部门支出性质
	public String queryDeptOut(Map<String, Object> entityMap) throws DataAccessException;

	// 发放方式
	public String queryEmpPay(Map<String, Object> entityMap) throws DataAccessException;

	// 凭证状态
	public String queryAccVouchState(Map<String, Object> entityMap) throws DataAccessException;

	// 科目级次
	public String querySubjLevel(Map<String, Object> entityMap) throws DataAccessException;

	// 科目
	public String querySubj(Map<String, Object> entityMap) throws DataAccessException;

	// 科目   账簿中的科目选择器
    public String querySubjByAccount(Map<String, Object> entityMap) throws DataAccessException;

	// 科目    不区分级次
	public String querySubjBylevel(Map<String, Object> entityMap) throws DataAccessException;
	
	// 科目全称
	public String querySubjAll(Map<String, Object> entityMap) throws DataAccessException;

	// 支出经济分类级次
	public String queryEcoLevel(Map<String, Object> entityMap) throws DataAccessException;

	// 支出功能分类级次
	public String queryFunLevel(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCheckItem(Map<String, Object> entityMap) throws DataAccessException;

	public String queryPayType(Map<String, Object> entityMap) throws DataAccessException;

	public String queryAccFinaContent(Map<String, Object> entityMap) throws DataAccessException;

	public String queryAccWageScheme(Map<String, Object> entityMap) throws DataAccessException;

	public String queryAccWageItem(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCheckItemById(Map<String, Object> entityMap) throws DataAccessException;

	// 根据部门加载职工列表
	public String queryEmpDictById(Map<String, Object> mapVo) throws DataAccessException;

	public String queryAccWage(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDeptDictNo(Map<String, Object> entityMap) throws DataAccessException;

	public String queryWageType(Map<String, Object> entityMap) throws DataAccessException;

	public String queryEmpDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryBankSelect(Map<String, Object> entityMap) throws DataAccessException;

	// 查询现金流量项目
	public String queryCashItemSelect(Map<String, Object> entityMap) throws DataAccessException;

	// 查询部门支出性质
	public String queryDeptOutSelect(Map<String, Object> entityMap) throws DataAccessException;
	
	// 查询患者类别 
	public String queryAccPatientType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 查询收费类别 
	public String queryAccChargeType(Map<String, Object> entityMap) throws DataAccessException;

	//查询报表字典
	public String querySuperReportDict(Map<String, Object> mapVo) throws DataAccessException;
	
	//查询制单人
	public String queryCreateUser(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 项目预算中查询收入项目,以410102或4201为前缀
	 * @param mapVo
	 * @return
	 */
	public String querySubjToProj(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryAllEmpDict(Map<String, Object> mapVo)throws DataAccessException;
	
	//查询制单人
	public String queryAccPayAttr(Map<String, Object> mapVo) throws DataAccessException;
	//核算分类下拉框
	public String queryCheckItemType(Map<String, Object> mapVo) throws DataAccessException;
	//科室字典(根据参数决定是否按权限)
	public String queryHosDept(Map<String, Object> mapVo) throws DataAccessException;
	//科室分类查询
	public String queryHosDeptKind(Map<String, Object> mapVo) throws DataAccessException;
	//工资项目性质字典
	public String queryAccWageItemNature(Map<String, Object> mapVo) throws DataAccessException;
	//工资项目类型字典
	public String queryAccWageItemType(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryBusiType(Map<String, Object> mapVo) throws DataAccessException;
	//会计年度
	public String queryAccYear(Map<String, Object> mapVo) throws DataAccessException;
	
	//基本指标
    public String queryAccTarget(Map<String, Object> mapVo) throws DataAccessException;

    //票据类型
    public String queryAccPaperType(Map<String, Object> mapVo) throws DataAccessException;

    public String queryAccWageItemColumn(Map<String, Object> mapVo) throws DataAccessException;
    
    public String queryBudgPaymentItemDict(Map<String, Object> mapVo) throws DataAccessException;
    
    public String queryBudgPaymentItemDictDept(Map<String, Object> mapVo) throws DataAccessException;
    
    public String queryBudgPaymentItem(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryBudgSysDict(Map<String, Object> mapVo) throws DataAccessException;
	
	public String querySubjDict(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryAccPaperCheques(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryAccWageColumn(Map<String, Object> mapVo) throws DataAccessException;
	//根据项目核算找会计科目
	public String querySubjByProjCheck(Map<String, Object> mapVo) throws DataAccessException;
	
	//查询自定义辅助核算字典对应表
	public String queryAccBusiZCheck(Map<String, Object> mapVo) throws DataAccessException;
	//根据项目核算找会计科目
	public String queryAccMultiPlan(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryAccYearMonth(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryAccTellType(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryGroupDict(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryHosInfo(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryHosCopy(Map<String, Object> mapVo) throws DataAccessException;
	
	//pj查询科目下拉框，根据条件匹配通用，不分页， value=code，text=code name
	public String querySubjCode(Map<String, Object> mapVo) throws DataAccessException;
	
	//pj查询科目下拉框，根据条件匹配通用，不分页，value=id，text=code name
	public String querySubjId(Map<String, Object> mapVo) throws DataAccessException;
	
	//pj根据科目编码，查询核算类型下拉框,根据条件匹配通用，不分页
	public String queryCheckTypeBySubjCode(Map<String, Object> mapVo) throws DataAccessException;
	
	//pj根据核算类型，查询核算项下拉框,根据条件匹配通用，不分页
	public String queryCheckItemByType(Map<String, Object> mapVo) throws DataAccessException;
	
	//根据核算类型，查询核算项下拉框,根据条件匹配通用，取前100条
	public String queryCheckItemByTypeFy(Map<String, Object> mapVo) throws DataAccessException;
	
	 //  出纳账管理    摘要字典 下拉框
	public String queryAccTellSummaryById(Map<String, Object> entityMap) throws DataAccessException;
	
	//his视图下拉框
	public String queryAccHisLog(Map<String, Object> mapVo) throws DataAccessException;
	//项目负责人
	public String queryProjEmp(Map<String, Object> mapVo) throws DataAccessException;
	//凭证来源
	public String queryBusiTypeByVouch(Map<String, Object> mapVo) throws DataAccessException;
	//集团化  ----会计科目  根据医院账套 进行级联
	public String querySubjByHosCopyRela(Map<String, Object> mapVo) throws DataAccessException;
	// 集团化  ----会计科目  根据医院账套 进行级联 查询科目下拉框，根据条件匹配通用，不分页， value=code，text=code name
	public String querySubjCodeByRela(Map<String, Object> mapVo) throws DataAccessException;
	// 集团化  ----辅助账中的会计科目  根据医院账套 进行级联 查询辅助核算中的科目下拉框
	public String querySubjByAccountRela(Map<String, Object> entityMap) throws DataAccessException;

	// 账簿方案列表
	public String queryAccBookSch(Map<String, Object> entityMap) throws DataAccessException;
	// 账簿方案--单位列表
	public String querySysHosAll(Map<String, Object> entityMap) throws DataAccessException;
	// 账簿方案--账套列表
	public String querySysHosCopyAll(Map<String, Object> entityMap) throws DataAccessException;
	//查找核算ID
	public String queryCheckTypeIdByCheckName(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCheckItemByTypeByCode(Map<String, Object> mapVo);

	public String queryEmpDictByWageCode(Map<String, Object> mapVo);
	
	// 自定义属性
	public String queryEmpSet(Map<String, Object> entityMap) throws DataAccessException;
	
	// 科目
	public String querySubjByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryDrugsPrepaType(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryAccDrugsPrepaType(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryHosSupDictUniversalMethod(Map<String, Object> entityMap) throws DataAccessException;
	
	// 付款方式(结算方式)
	public String queryAccPayType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 查询仓库(虚仓)
	public String queryAccVirStore(Map<String, Object> entityMap) throws DataAccessException;
	
	//通过参数控制 仓库权限 
    public String queryAccStoreDictDate(Map<String, Object> entityMap) throws DataAccessException;
    
    public String querySysUser(Map<String, Object> entityMap) throws DataAccessException;
    
    // 供应商停用不显示
 	public String queryHosSupDictDisable(Map<String, Object> entityMap) throws DataAccessException;
 	
 	// 采购发票 付款条件下拉框
 	public String queryAccPayTerm(Map<String, Object> entityMap) throws DataAccessException;
 	
 	//通过参数控制 编制科室权限 
    public String queryAccDeptDictDate(Map<String, Object> entityMap) throws DataAccessException;
    
    /**
	 * 资产性质
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAccNaturs(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryHosSupDictNo(Map<String, Object> mapVo);
	
	/**
	 * 合同
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryContractMain(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 供应商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosSupDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 仓库下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosStoreDict(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public String queryGroupDictDataType(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryHosInfoDataType(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryHosCopyDataType(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryAccMedTypeHis(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询业务字典表下拉框
	 * @param mapVo
	 * @return
	 */
	public String queryHosDictTypeDict(Map<String, Object> mapVo);

	public String queryInitAccDict(Map<String, Object> mapVo);
	
	public String queryAccYewuType(Map<String, Object> mapVo) throws DataAccessException;
	public String queryAccYewuDict(Map<String, Object> mapVo) throws DataAccessException;
	public String queryAccBudgRange(Map<String, Object> mapVo) throws DataAccessException;
}
