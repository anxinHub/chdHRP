package com.chd.hrp.ass.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.HrpAssSelect;
import com.chd.hrp.ass.entity.dict.AssMaintainItemDict;
import com.chd.hrp.ass.entity.dict.AssNoDict;
import com.chd.hrp.sys.entity.HrpSysSelect;

public interface HrpAssSelectMapper extends SqlMapper {  
 
	/**
	 * 资产分类下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssTypeDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException; 
 
	/**
	 * 资产分类下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssTypeDictIsLast(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产分类上级编码下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssTypeSuperCode(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 仓库下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryHosStoreDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 仓库下拉框检索(集团调拨使用)
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAllotHosStoreDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 生产厂商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryHosFacDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 供应商下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryHosSupDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产用途下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssUsageDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产折旧方法下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssDepreMethodDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产变更字典下拉框检索
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssNoDict> queryAssNoDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产变更字典下拉框检索，表格形式
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssNoDict> queryAssNoDictTable(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 科室下拉框
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryDeptDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 资产合同
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryContractMain(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 职工字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryHeadEmp(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产性质
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssNaturs(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产业务类型
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssBusType(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产卡片号
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssCardNoDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 申请单号
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssApplyNoDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	public List<HrpAssSelect> queryAssDeperMethodDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 保养计划
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssMaintainPlanDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 保养项目字典下拉框检索，表格形式
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssMaintainItemDict> queryAssMaintainItemTable(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 计量计划
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssMeasurePlanDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资金来源字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> querySourceDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;
	/**
	 * 设备来源字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryDeviceDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;
	/**
	 * 申购类别字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryBuyDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 计量单位字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryHosUnitDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 集团对应的医院字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryHosInfoDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 集团字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryGroupDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	public List<HrpAssSelect> queryContractMainDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	public List<HrpAssSelect> queryMatFinaType(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	public List<HrpAssSelect> queryMatFinaTypeIsLast(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// public List<AssCard> queryAssCardListBox(Map<String, Object> entityMap,
	// RowBounds rowBounds) throws DataAccessException;

	/**
	 * 获取当前用户科室默认值
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryDeptDictInitValue(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 资产维护快速定位
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssNoDictTree(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 文档类别
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpAssSelect> queryAssFileTypeDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 根据资产ID查询验收项目
	public List<HrpAssSelect> queryAssAcceptItemAffi(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 查询验收项目字典
	public List<HrpAssSelect> queryAssAcceptItemDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 建筑结构
	public List<HrpAssSelect> queryAssStructDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 土地来源
	public List<HrpAssSelect> queryAssLandSourceDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 产权形式
	public List<HrpAssSelect> queryAssPropDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 文物等级
	public List<HrpAssSelect> queryAssRelicGradeDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 用户查询列表
	public List<HrpAssSelect> queryUserDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 入库单
	public List<HrpAssSelect> queryAssInMainDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 条码类型
	public List<HrpAssSelect> queryAssBarTypeDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 卡片使用状态
	public List<HrpAssSelect> queryAssCardUseStateDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 处置类型
	public List<HrpAssSelect> queryAssDisposeTypeDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 项目字典
	public List<HrpAssSelect> queryAssProjDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 生产厂商类别
	public List<HrpAssSelect> queryFacTypeDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	public List<HrpAssSelect> queryHosFac(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	//
	public List<HrpAssSelect> queryMatPayTerm(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 主表库房是否停用
	public List<HrpAssSelect> queryMatYearOrNo(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 库房分类
	public List<HrpAssSelect> queryMatStoreType(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 职能科室
	public List<HrpAssSelect> queryMatDeptIsManager(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 普通职工/领料人 --职工表
	public List<HrpAssSelect> queryMatEmp(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 采购人
	public List<HrpAssSelect> queryMatStockEmp(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 库房管理
	public List<HrpAssSelect> queryMatManagerEmp(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	public List<HrpAssSelect> queryHosCopyDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	// 根据条件判断是否查存在对应卡片
	public Integer queryAssExistsSpecialCard(Map<String, Object> entityMap) throws DataAccessException;

	public Integer queryAssExistsGeneralCard(Map<String, Object> entityMap) throws DataAccessException;

	public Integer queryAssExistsHouseCard(Map<String, Object> entityMap) throws DataAccessException;

	public Integer queryAssExistsInassetsCard(Map<String, Object> entityMap) throws DataAccessException;

	public Integer queryAssExistsOtherCard(Map<String, Object> entityMap) throws DataAccessException;

	public Integer queryAssExistsLandCard(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 资产卡片号下拉框检索，表格形式 （专用设备）
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssCardSpecial(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产卡片号下拉框检索，表格形式 （一般设备）
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssCardGeneral(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产卡片号下拉框检索，表格形式 （房屋及建筑）
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssCardHouse(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产卡片号下拉框检索，表格形式 （其他无形资产）
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssCardInassets(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产卡片号下拉框检索，表格形式 （其他固定资产）
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssCardOther(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产卡片号下拉框检索，表格形式 （土地来源）
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssCardLand(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	public List<Map<String, Object>> queryAssCardAll(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	public List<HrpAssSelect> queryAssIsDept(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产改造的业务类型
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBusType(Map<String, Object> mapVo, RowBounds rowBounds);

	public List<Map<String, Object>> queryHosDeptTerr(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPayStageGrid(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	List<Map<String, Object>> queryBillDetailGrid(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	public Integer queryBillDetailExists(Map<String, Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> queryBackBillDetailGrid(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	public Integer queryBackBillDetailExists(Map<String, Object> entityMap) throws DataAccessException;

	public List<HrpAssSelect> queryAccPayType(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 维修班组下拉框
	 * 
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryRepTeam(Map<String, Object> mapVo, RowBounds rowBounds);

	/**
	 * 维修材料下拉框
	 * 
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryAssInvArrt(Map<String, Object> mapVo, RowBounds rowBounds);

	public List<HrpAssSelect> querySupTypeDict(Map<String, Object> mapVo, RowBounds rowBounds);

	/**
	 * 资产分类快速查询
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssFinaDictTree(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 财务分类上级下拉框(cjc)
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryMatFinaTypes(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 资产分类快速查找(cjc)
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryAssTypeDictTree(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 国标码字典下拉框
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssGBDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 维修申请资产卡片下拉框表单
	 * 
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<AssNoDict> queryAssCardNoDictTable(Map<String, Object> mapVo, RowBounds rowBounds)
			throws DataAccessException;

	public List<Map<String, Object>> queryDeptKindDict(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 上级位置下拉
	 * 
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> querySuperLocationSelect(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 上级故障下拉框
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querySuperFaultTypeSelect(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryAssCardNoDictSelect(Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryNotExistsLocationSelect(Map<String, Object> mapVo) throws DataAccessException;

	public List<AssMaintainItemDict> queryAssMaintainItem(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    /**
     * 仓库所对应的管理部门
     * @param entityMap
     * @param rowBounds
     * @return
     */
	public List<HrpAssSelect> queryAssStoreDept(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public List<HrpAssSelect> queryDeptDictAll(Map<String, Object> entityMap) throws DataAccessException;

	public List<HrpAssSelect> queryHosSupDictNo(Map<String, Object> entityMap,
			RowBounds rowBounds);

	public List<HrpAssSelect> queryHosFacDictNo(Map<String, Object> entityMap,
			RowBounds rowBounds);

	public List<HrpAssSelect> queryCertNo(Map<String, Object> entityMap, RowBounds rowBounds);
	
	public List<HrpAssSelect> queryAssMeasureKingDict(Map<String, Object> entityMap, RowBounds rowBounds);
	
	
	public List<HrpAssSelect> queryAccEmpAttr(Map<String, Object> entityMap);
	
	
	public List<HrpAssSelect> queryReportBusType(Map<String, Object> entityMap);
	
	public List<HrpAssSelect> queryAssTypeSixEight(Map<String, Object> entityMap, RowBounds rowBounds);
	
	/**
	 * 购置申请  预算信息 下拉框检索
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryAssBudgTable(Map<String, Object> entityMap,	RowBounds rowBounds)throws DataAccessException;
	
	
	public List<HrpAssSelect> queryPactMainFkht(Map<String, Object> entityMap);

}
