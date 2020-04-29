package com.chd.hrp.ass.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.ass.entity.dict.AssNoDict;

 
public interface HrpAssSelectService { 
	/**
	 * 资产分类下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 资产分类下拉框检索 
	 *  
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssTypeDictIsLast(Map<String, Object> entityMap) throws DataAccessException; 

	/**
	 * 资产分类上级编码下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssTypeSuperCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 仓库下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosStoreDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 仓库下拉框检索（集团调拨使用）
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAllotHosStoreDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 生产厂商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosFacDict(Map<String, Object> entityMap) throws DataAccessException;

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
	 * 资产用途下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssUsageDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 资产折旧方法下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssDepreMethodDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 资产变更字典下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssNoDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 资产变更字典下拉框检索,表格形式
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssNoDictTable(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 科室下拉框
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 职工
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHeadEmp(Map<String, Object> entityMap) throws DataAccessException;

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
	 * 资产性质
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssNaturs(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 资产业务类型
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssBusType(Map<String, Object> entityMap) throws DataAccessException;


	/**
	 * 申请单号
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssApplyNoDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 保养计划
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssMaintainPlanDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 保养项目字典下拉框检索，表格形式
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssMaintainItemTable(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 计量计划
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssMeasurePlanDict(Map<String, Object> entityMap) throws DataAccessException;


	public String queryAssDeperMethodDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryAssCardTable(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 设备来源字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String querySourceDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 资金来源字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeviceDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 申购类别字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBuyDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 计量单位字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosUnitDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 集团对应医院字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosInfoDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 集团字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryGroupDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryContractMainDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryMatFinaType(Map<String, Object> entityMap) throws DataAccessException;

	public String queryMatFinaTypeIsLast(Map<String, Object> entityMap) throws DataAccessException;


	/**
	 * 科室默认值
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptDictInitValue(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 资产维护快速定位
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssNoDictTree(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 文档类别
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssFileTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	// 查询验收项目
	public String queryAssAcceptItem(Map<String, Object> entityMap) throws DataAccessException;

	// 建筑结构
	public String queryAssStructDict(Map<String, Object> entityMap) throws DataAccessException;

	// 土地来源
	public String queryAssLandSourceDict(Map<String, Object> entityMap) throws DataAccessException;

	// 产权形式
	public String queryAssPropDict(Map<String, Object> entityMap) throws DataAccessException;

	// 文物等级
	public String queryAssRelicGradeDict(Map<String, Object> entityMap) throws DataAccessException;

	// 用户查询列表
	public String queryUserDict(Map<String, Object> entityMap) throws DataAccessException;

	// 入库单
	public String queryAssInMainDict(Map<String, Object> entityMap) throws DataAccessException;

	// 条码类型
	public String queryAssBarTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//卡片使用状态
	public String queryAssCardUseStateDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//处置状态
	public String queryAssDisposeTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//项目字典
	public String queryAssProjDict(Map<String, Object> entityMap) throws DataAccessException;
    //生产厂商类别
	public String queryFacTypeDict(Map<String, Object> entityMap)throws DataAccessException;

	public String queryHosFac(Map<String, Object> entityMap)throws DataAccessException;
  //采购发票    付款条件下拉框
	public String queryMatPayTerm(Map<String, Object> entityMap)throws DataAccessException;
   //主表 库房是否停用
	public String queryMatYearOrNo(Map<String, Object> entityMap)throws DataAccessException;
	//库房分类 queryMatStoreType
	public String queryMatStoreType(Map<String, Object> mapVo)throws DataAccessException;
//职能科室
	public String queryMatDeptIsManager(Map<String, Object> mapVo)throws DataAccessException;
	//普通职工/领料人  --查员工表
	public String queryMatEmp(Map<String, Object> mapVo)throws DataAccessException;
//采购人
	public String queryMatStockEmp(Map<String, Object> mapVo)throws DataAccessException;
//库房管理
	public String queryMatManagerEmp(Map<String, Object> mapVo)throws DataAccessException;
	
	
	public String queryHosCopyDict(Map<String, Object> mapVo)throws DataAccessException;
	
	public String queryAssIsDept(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 资产改造所用业务类型
	 * @param mapVo
	 * @return
	 */
	public String queryBusType(Map<String, Object> mapVo)throws DataAccessException;
	
	public String queryPayStageGrid(Map<String, Object> mapVo)throws DataAccessException;
	
	public String queryAccPayType(Map<String, Object> mapVo)throws DataAccessException;
	
	public String queryBillDetailGrid(Map<String, Object> mapVo)throws DataAccessException;
	
	public String queryBackBillDetailGrid(Map<String, Object> mapVo)throws DataAccessException;
	
	public String queryAssCardNoDict(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 科室tree
	 * @param mapVo
	 * @return
	 */
	public String queryHosDeptTerr(Map<String, Object> mapVo);
	/**
	 * 维修班组下拉框
	 */
	public String queryRepTeam(Map<String, Object> mapVo);

	public String queryAssInvArrt(Map<String, Object> mapVo);

	public String queryAssCardSpecial(Map<String, Object> entityMap);

	public String queryAssCardGeneral(Map<String, Object> entityMap);

	public String queryAssCardHouse(Map<String, Object> entityMap);

	public String queryAssCardInassets(Map<String, Object> entityMap);

	public String queryAssCardOther(Map<String, Object> entityMap);

	public String queryAssCardLand(Map<String, Object> entityMap);

	public String querySupTypeDict(Map<String, Object> mapVo);

	public String queryAssFinaDictTree(Map<String, Object> mapVo);
	/**
	 *财务分类上级下拉框(cjc)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatFinaTypes(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 资产分类快速查询(cjc)
	 * @param mapVo
	 * @return
	 */
	public String queryAssTypeDictTree(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 国标码字典下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssGBDict(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 维修申请资产卡片下拉框表单
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssCardNoDictTable(Map<String, Object> mapVo)throws DataAccessException;

	
	public String queryDeptKindDict(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 上级位置
	 * @param mapVo
	 * @return
	 */
	public String querySuperLocationSelect(Map<String, Object> mapVo);

	public String querySuperFaultTypeSelect(Map<String, Object> mapVo);

	public String queryAssCardNoDictSelect(Map<String, Object> mapVo);

	public String queryNotExistsLocationSelect(Map<String, Object> mapVo);

	public String queryAssMaintainItem(Map<String, Object> mapVo);
	
	public String queryAssStoreDept(Map<String, Object> mapVo);
	
	public String queryDeptDictAll(Map<String, Object> mapVo);

	public String queryHosSupDictNo(Map<String, Object> mapVo);

	public String queryHosFacDictNo(Map<String, Object> mapVo);

	public String queryCertNo(Map<String, Object> mapVo);
	
	public String queryAssMeasureKingDict(Map<String, Object> mapVo);
	
	public String queryAccEmpAttr(Map<String, Object> mapVo);
	
	public String queryReportBusType(Map<String, Object> mapVo);
	
	public String queryAssTypeSixEight(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 购置申请  预算信息 下拉框检索
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */

	public String queryAssBudgTable(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryPactMainFkht(Map<String, Object> mapVo) throws DataAccessException;
}
