package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.mat.entity.HrpMatSelect;

public interface HrpAccSelectMapper extends SqlMapper {
	// 五性分析指标类别
	public List<HrpAccSelect> queryAccWxType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 基本分析指标类别
	public List<HrpAccSelect> queryAccBasType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 科目类别
	public List<HrpAccSelect> querySubjType(Map<String, Object> entityMap) throws DataAccessException;
	// 科目类别 
	public List<HrpAccSelect> querySubjTypeKind(Map<String, Object> entityMap) throws DataAccessException;

	// 科目性质
	public List<HrpAccSelect> querySubjNature(Map<String, Object> entityMap) throws DataAccessException;

	// 凭证类型
	public List<HrpAccSelect> queryVouchType(Map<String, Object> entityMap) throws DataAccessException;

	// 币种
	public List<HrpAccSelect> queryCur(Map<String, Object> entityMap) throws DataAccessException;

	// 核算类型
	public List<HrpAccSelect> queryCheckType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 支出功能分类
	public List<HrpAccSelect> queryFunType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 支出经济分类
	public List<HrpAccSelect> queryEcoType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 现金流量类别
	public List<HrpAccSelect> queryCashType(Map<String, Object> entityMap) throws DataAccessException;

	// 部门类型
	public List<HrpAccSelect> queryDeptType(Map<String, Object> entityMap) throws DataAccessException;

	// 部门性质
	public List<HrpAccSelect> queryDeptNatur(Map<String, Object> entityMap) throws DataAccessException;

	// 部门支出性质
	public List<HrpAccSelect> queryDeptOut(Map<String, Object> entityMap) throws DataAccessException;

	// 发放方式
	public List<HrpAccSelect> queryEmpPay(Map<String, Object> entityMap) throws DataAccessException;

	public List<HrpAccSelect> queryAccVouchState(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 科目级次
	public List<HrpAccSelect> querySubjLevel(Map<String, Object> entityMap) throws DataAccessException;

	// 科目
	public List<HrpAccSelect> querySubj(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 科目  账簿中的科目选择器
	public List<HrpAccSelect> querySubjByAccount(Map<String, Object> entityMap) throws DataAccessException;

		
	// 科目   不区分级次
	public List<HrpAccSelect> querySubjBylevel(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
	// 科目全称
	public List<HrpAccSelect> querySubjAll(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 支出经济分类级次
	public List<HrpAccSelect> queryEcoLevel(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 支出功能分类级次
	public List<HrpAccSelect> queryFunLevel(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<HrpAccSelect> queryYearMonth(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<HrpAccSelect> queryCheckItem(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<HrpAccSelect> queryPayType(Map<String, Object> entityMap) throws DataAccessException;

	// 财政补助内容
	public List<HrpAccSelect> queryAccFinaContent(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 工资方案
	public List<HrpAccSelect> queryAccWageScheme(Map<String, Object> entityMap) throws DataAccessException;

	// 工资项目
	public List<HrpAccSelect> queryAccWageItem(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 工资套
	public List<HrpAccSelect> queryAccWage(Map<String, Object> entityMap) throws DataAccessException;

	// 部门
	public List<HrpAccSelect> queryDeptDictNo(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 自定义辅助核算
	public List<HrpAccSelect> queryCheckItemById(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 根据部门加载职工列表
	public List<HrpAccSelect> queryEmpDictById(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;

	// 账户类别
	public List<HrpAccSelect> queryWageType(Map<String, Object> mapVo) throws DataAccessException;

	// 根据职工分类加载职工列表
	public List<HrpAccSelect> queryEmpDict(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;

	// 查询银行信息列表
	public List<HrpAccSelect> queryBankSelect(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;

	// 查询现金流量项目列表
	public List<HrpAccSelect> queryCashItemSelect(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;

	// 自动凭证模块类别表
	public List<HrpAccSelect> querySysBusiMod(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;

	// 查询部门支出性质
	public List<HrpAccSelect> queryDeptOutSelect(Map<String, Object> mapVo) throws DataAccessException;
	
	// 查询患者类别 
	public List<HrpAccSelect> queryAccPatientType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	// 查询收费类别
	public List<HrpAccSelect> queryAccChargeType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	//查询报表字典
	public List<HrpAccSelect> querySuperReportDict(Map<String, Object> mapVo) throws DataAccessException;
	
	//查询制单人
	public List<HrpAccSelect> queryCreateUser(Map<String, Object> mapVo) throws DataAccessException;
		
	//查询制单人
	public List<HrpAccSelect> queryAccPayAttr(Map<String, Object> mapVo) throws DataAccessException;
	//核算分类下拉框
	public List<HrpAccSelect> queryCheckItemType(Map<String, Object> mapVo) throws DataAccessException;
	//科室字典(根据参数决定是否按权限)
	public List<HrpAccSelect> queryHosDept(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	//工资项目性质字典
	public List<HrpAccSelect> queryHosDeptKind(Map<String, Object> mapVo) throws DataAccessException;
	//工资项目性质字典
	public List<HrpAccSelect> queryWageItemNature(Map<String, Object> mapVo) throws DataAccessException;
	//工资项目类型字典
	public List<HrpAccSelect> queryWageItemType(Map<String, Object> mapVo) throws DataAccessException;
	
	
	public List<HrpAccSelect> queryBusiType(Map<String, Object> mapVo) throws DataAccessException;
	//会计年度
	public List<HrpAccSelect> queryAccYear(Map<String, Object> mapVo) throws DataAccessException;
	//基本指标
	public List<HrpAccSelect> queryAccTarget(Map<String, Object> mapVo) throws DataAccessException;
	
	//票据类型
	public List<HrpAccSelect> queryAccPaperType(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<HrpAccSelect> queryAccWageItemColumn(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<HrpAccSelect> queryBudgPaymentItemDict(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<HrpAccSelect> queryBudgPaymentItemDictDept(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<HrpAccSelect> queryBudgPaymentItem(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<HrpAccSelect> queryBudgSysDict(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<HrpAccSelect> querySubjDict(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<HrpAccSelect> queryAccWageColumn(Map<String, Object> mapVo) throws DataAccessException;
	//根据项目核算查找会计科目
	public List<HrpAccSelect> querySubjByProjCheck(Map<String, Object> mapVo) throws DataAccessException;
	
	//查询自定义辅助核算字典对应表
	public List<HrpAccSelect> queryAccBusiZCheck(Map<String, Object> mapVo) throws DataAccessException;
	//查询多栏方案
	public List<HrpAccSelect> queryAccMultiPlan(Map<String, Object> mapVo) throws DataAccessException;
	//加载当前机构所有员工
	public List<HrpAccSelect> queryAllEmpDict(Map<String, Object> mapVo);
	
	public List<HrpAccSelect> queryAccYearMonth(Map<String, Object> mapVo);
	/**
	 * 项目预算中查询收入项目,以410102或4201为前缀
	 * @param mapVo
	 * @return
	 */
	public List<HrpAccSelect> querySubjToProj(Map<String, Object> mapVo);
	
	public List<HrpAccSelect> queryAccTellType(Map<String, Object> mapVo);
	
	public List<HrpAccSelect> queryGroupDict(Map<String, Object> mapVo);
	
	public List<HrpAccSelect> queryHosInfo(Map<String, Object> mapVo);
	
	public List<HrpAccSelect> queryHosCopy(Map<String, Object> mapVo);
	
	//pj查询科目下拉框，根据条件匹配通用，不分页， value=code，text=code name
	public List<HrpAccSelect> querySubjCode(Map<String, Object> mapVo) throws DataAccessException;
	
	//pj查询科目下拉框，根据条件匹配通用，不分页，value=id，text=code name
	public List<HrpAccSelect> querySubjId(Map<String, Object> mapVo) throws DataAccessException;
	
	//pj根据科目编码，查询核算类型下拉框,根据条件匹配通用，不分页
	public List<HrpAccSelect> queryCheckTypeBySubjCode(Map<String, Object> mapVo) throws DataAccessException;
	
	//pj根据核算类型ID，查询核算类型，为了queryCheckItemByType动态查询核算项
	public Map<String,Object> queryCheckTypeById(Map<String, Object> mapVo) throws DataAccessException;
	
	//pj根据核算类型，查询核算项下拉框,根据条件匹配通用，不分页
	public List<HrpAccSelect> queryCheckItemByType(Map<String, Object> mapVo) throws DataAccessException;
	//根据核算类型，查询核算项下拉框,根据条件匹配通用取前100条
	public List<HrpAccSelect> queryCheckItemByTypeFy(Map<String, Object> mapVo) throws DataAccessException;
	
	 //  出纳账管理    摘要字典 下拉框
	public List<HrpAccSelect> queryAccTellSummaryById(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	//his视图下拉框
	public List<HrpAccSelect> queryAccHisLog(Map<String, Object> mapVo);
	//项目负责人
	public List<HrpAccSelect> queryProjEmp(Map<String, Object> mapVo);
	//凭证来源
	public List<HrpAccSelect> queryBusiTypeByVouch(Map<String, Object> entityMap) throws DataAccessException;
	//集团化  -----会计科目  根据医院账套进行级联  
	public List<HrpAccSelect> querySubjByHosCopyRela(Map<String, Object> entityMap) throws DataAccessException;
	// 集团化  ----会计科目  根据医院账套 进行级联 查询科目下拉框，根据条件匹配通用，不分页， value=code，text=code name
	public List<HrpAccSelect> querySubjCodeByRela(Map<String, Object> mapVo) throws DataAccessException;
	// 集团化    ---辅助账   科目  账簿中的科目选择器
	public List<HrpAccSelect> querySubjByAccountRela(Map<String, Object> entityMap) throws DataAccessException;

	// 账簿方案列表
	public List<HrpAccSelect> queryAccBookSch(Map<String, Object> entityMap) throws DataAccessException;
	// 账簿方案--单位列表
	public List<HrpAccSelect> querySysHosAll(Map<String, Object> entityMap) throws DataAccessException;
	// 账簿方案--账套列表
	public List<HrpAccSelect> querySysHosCopyAll(Map<String, Object> entityMap) throws DataAccessException;
	// 查核算项ID
	public HrpAccSelect queryCheckTypeIdByCheckName(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HrpAccSelect> queryEmpDictByWageCode(Map<String, Object> mapVo);

	public List<HrpAccSelect> queryAccPaperCheques(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	public List<HrpAccSelect> queryEmpSet(Map<String, Object> entityMap) throws DataAccessException;
	
	// 科目
    public List<HrpAccSelect> querySubjByCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    
	public List<HrpAccSelect> queryDrugsPrepaType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HrpAccSelect> queryAccDrugsPrepaType(Map<String, Object> mapVo) throws DataAccessException;
 	
 	public List<HrpAccSelect> queryHosSupDictUniversalMethod(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
 	//付款方式(结算方式)
 	public List<HrpAccSelect> queryAccPayType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
 	
 	//查询仓库(虚仓)
 	public List<HrpAccSelect> queryAccVirStore(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
 		
 	//通过参数控制 仓库权限 
 	public List<HrpAccSelect> queryAccStoreDictDate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
 	  
 	//查询制单人  
    public List<HrpAccSelect> querySysUser(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;

    //采购发票 付款条件下拉框
  	public List<HrpAccSelect> queryAccPayTerm(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
  	
  	//通过参数控制 编制科室权限 
  	public List<HrpAccSelect> queryAccDeptDictDate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
  	
  	public List<HrpMatSelect> queryHosSupDictDisable(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
  	public List<HrpAccSelect> queryAccYewuType(Map<String, Object> entityMap) throws DataAccessException;
  	public List<HrpAccSelect> queryAccYewuDict(Map<String, Object> entityMap) throws DataAccessException;
  	public List<HrpAccSelect> queryAccBudgRange(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产性质
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAccSelect> queryAccNaturs(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;
	
	public List<HrpAccSelect> queryHosSupDictNo(Map<String, Object> entityMap,
			RowBounds rowBounds);
	
	/**
	 * 资产合同
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAccSelect> queryContractMain(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;
	
	/**
	 * 供应商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAccSelect> queryHosSupDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;
	
	/**
	 * 仓库下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAccSelect> queryHosStoreDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;
	
	
	public List<HrpAccSelect> queryGroupDictDataType(Map<String, Object> mapVo);
	
	public List<HrpAccSelect> queryHosInfoDataType(Map<String, Object> mapVo);
	
	public List<HrpAccSelect> queryHosCopyDataType(Map<String, Object> mapVo);
	
	
	public List<HrpAccSelect> queryAccMedTypeHis(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	//查询业务字典表下拉框
	public List<HrpAccSelect> queryHosDictTypeDict(Map<String, Object> mapVo);

	public List<HrpAccSelect> queryInitAccDict(Map<String, Object> mapVo);
}
