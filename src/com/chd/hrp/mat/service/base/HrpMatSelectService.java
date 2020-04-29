package com.chd.hrp.mat.service.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.mat.entity.HrpMatSelect;

public interface HrpMatSelectService {
   
	// 是否
	public String queryMatYearOrNo(Map<String, Object> entityMap) throws DataAccessException;

	// 物资类别
	public String queryMatType(Map<String, Object> entityMap) throws DataAccessException;

	// 物资类别变更
	public String queryMatTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	// 物资类别变更 
	public String queryMatTypeDictByWrite(Map<String, Object> entityMap) throws DataAccessException;

	// 物资类别变更
	public String queryMatTypeDicts(Map<String, Object> entityMap) throws DataAccessException;

	// 物资类别变更加编码
	public String queryMatTypeDictCode(Map<String, Object> entityMap) throws DataAccessException;

	// 物资
	public String queryMatInv(Map<String, Object> entityMap) throws DataAccessException;

	// 物资变更
	public String queryMatInvDict(Map<String, Object> entityMap) throws DataAccessException;

	// 财务分类
	public String queryMatFinaType(Map<String, Object> entityMap) throws DataAccessException;

	// 供应商
	public String queryHosSup(Map<String, Object> entityMap) throws DataAccessException;

	// 供应商变更
	public String queryHosSupDict(Map<String, Object> entityMap) throws DataAccessException;

	// 供应商停用不显示
	public String queryHosSupDictDisable(Map<String, Object> entityMap) throws DataAccessException;

	// 生成厂商
	public String queryHosFac(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHosFacInv(Map<String, Object> entityMap) throws DataAccessException;

	// 生成厂商变更
	public String queryHosFacDict(Map<String, Object> entityMap) throws DataAccessException;

	// 计量单位
	public String queryHosUnit(Map<String, Object> entityMap) throws DataAccessException;

	// 获取内置数据
	public String queryMatSysList(Map<String, Object> entityMap) throws DataAccessException;

	// 货位分类
	public String queryMatLocationType(Map<String, Object> entityMap) throws DataAccessException;

	// 货位字典
	public String queryMatLocationDict(Map<String, Object> entityMap) throws DataAccessException;

	// 当前用户查看有权限的仓库
	public String queryMatStore(Map<String, Object> entityMap) throws DataAccessException;

	public String queryMatStoredisable(Map<String, Object> entityMap) throws DataAccessException;

	// 当前用户查看只有读的权限的仓库
	public String queryMatStoreByRead(Map<String, Object> entityMap) throws DataAccessException;

	public String queryMatStoreByReaddisable(Map<String, Object> entityMap) throws DataAccessException;

	// 当前用户查看只有写的权限的仓库
	public String queryMatStoreByWrite(Map<String, Object> entityMap) throws DataAccessException;

	// 职工/领料人 --职工表
	public String queryMatEmp(Map<String, Object> entityMap) throws DataAccessException;

	// 普通职工/领料人 --职工变更表
	public String queryMatEmpDict(Map<String, Object> entityMap) throws DataAccessException;

	// 职能科室
	public String queryMatDeptIsManager(Map<String, Object> entityMap) throws DataAccessException;

	// 库房管理员
	public String queryMatManagerEmp(Map<String, Object> entityMap) throws DataAccessException;

	// 采购人
	public String queryMatStockEmp(Map<String, Object> entityMap) throws DataAccessException;

	// 采购人 --变更表
	public String queryMatStockEmpDict(Map<String, Object> entityMap) throws DataAccessException;

	// 采购人 一个仓库对应多个采购员 查询 即墨需求 2017/04/06 gaopei
	public String queryMatStockEmpByStore(Map<String, Object> entityMap) throws DataAccessException;

	// 库房分类
	public String queryMatStoreType(Map<String, Object> entityMap) throws DataAccessException;

	// 计划状态
	public String queryMatPlanState(Map<String, Object> entityMap) throws DataAccessException;

	// 编制科室
	public String queryMatDeptDict(Map<String, Object> entityMap) throws DataAccessException;
	
   //通过参数控制 编制科室权限 
    public String queryMatDeptDictDate(Map<String, Object> entityMap) throws DataAccessException;
    
  //通过参数控制 物资类别权限 
    public String queryMatTypeDictDate(Map<String, Object> entityMap) throws DataAccessException;
    
    
    //通过参数控制 仓库权限 
    public String queryMatStoreDictDate(Map<String, Object> entityMap) throws DataAccessException;
    
    //库房处只能选到‘是否采购库房’为是的仓库
    public String queryMatStoreDictPro(Map<String, Object> entityMap) throws DataAccessException;
    
	//盘点科室


	// 盘点科室

	public String queryMatPDDeptDict(Map<String, Object> entityMap) throws DataAccessException;

	// 物资仓库配套表信息 
	public String queryMatStoreMatch(Map<String, Object> entityMap) throws DataAccessException;

	// 物资科室配套表信息
	public String queryMatDeptMatch(Map<String, Object> entityMap) throws DataAccessException;

	// 物资仓库配套表信息 只读
	public String queryMatStoreMatchRead(Map<String, Object> entityMap) throws DataAccessException;

	// 物资科室配套表信息 只读
	public String queryMatDeptMatchRead(Map<String, Object> entityMap) throws DataAccessException;

	// 获取年份
	public String queryMatYear(Map<String, Object> entityMap) throws DataAccessException;

	// 获取月份
	public String queryMatMonth(Map<String, Object> entityMap) throws DataAccessException;

	// 当前用户查看有权限的科室
	public String queryMatDept(Map<String, Object> entityMap) throws DataAccessException;

	// 业务类型
	public String queryMatBusType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 业务类型
	public String queryMatBusTypes(Map<String, Object> entityMap) throws DataAccessException;

	// 采购协议类别
	public String queryMatProtocolType(Map<String, Object> entityMap) throws DataAccessException;

	// 当前用户查看有权限的科室并且是采购科室
	public String queryPurDept(Map<String, Object> entityMap) throws DataAccessException;

	// 计划类型
	public String queryMatPlanType(Map<String, Object> entityMap) throws DataAccessException;

	// 采购计划-采购员
	public String queryMatPurStockEmp(Map<String, Object> entityMap) throws DataAccessException;

	// 包装单位
	public String queryMatHosPackage(Map<String, Object> entityMap) throws DataAccessException;

	// 采购类型
	public String queryMatStockType(Map<String, Object> entityMap) throws DataAccessException;

	// 采购协议
	public String queryMatProtocolMain(Map<String, Object> entityMap) throws DataAccessException;
	// 付款协议
	public String queryMatPactFkxyMain(Map<String, Object> entityMap) throws DataAccessException;

	// 付款方式(结算方式)
	public String queryMatPayType(Map<String, Object> entityMap) throws DataAccessException;

	// 科室
	public String queryHosDept(Map<String, Object> entityMap) throws DataAccessException;

	// 科室--变更
	public String queryHosDeptDict(Map<String, Object> entityMap) throws DataAccessException;

	// 科室--权限
	public String queryHosDeptByPerm(Map<String, Object> entityMap) throws DataAccessException;

	// 科室--变更--权限
	public String queryHosDeptDictByPerm(Map<String, Object> entityMap) throws DataAccessException;

	// 查询单位带变更号
	public String queryMatHosInfoDict(Map<String, Object> entityMap) throws DataAccessException;

	// 查询单位
	public String queryMatHosInfo(Map<String, Object> entityMap) throws DataAccessException;

	// 根据仓库对应关系查询材料
	public String queryMatInvDictByStoreInv(Map<String, Object> entityMap) throws DataAccessException;

	// 仓库(带权限) 并且带 别名
	public String queryMatStoreAlias(Map<String, Object> entityMap) throws DataAccessException;

	// 材料证件分类
	public String queryMatInvCertType(Map<String, Object> entityMap) throws DataAccessException;

	// 证件编码 下拉框
	public String queryMatInvCert(Map<String, Object> entityMap) throws DataAccessException;

	// 供应商类别 下拉框
	public String queryHosSupType(Map<String, Object> entityMap) throws DataAccessException;

	// 供应商类别 下拉框
	public String queryMatVenCertType(Map<String, Object> entityMap) throws DataAccessException;

	// 物资分类级次
	public String queryMatTypeLevel(Map<String, Object> entityMap) throws DataAccessException;

	// 物资分类级次-带id
	public String queryMatTypeLevel_2(Map<String, Object> entityMap) throws DataAccessException;

	// 包装单位下拉框
	public String queryHosPackage(Map<String, Object> entityMap) throws DataAccessException;

	// 签订部门：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
	public String querySignedDept(Map<String, Object> entityMap) throws DataAccessException;

	// 采购发票 付款条件下拉框
	public String queryMatPayTerm(Map<String, Object> entityMap) throws DataAccessException;

	// 项目下拉框
	public String queryMatProj(Map<String, Object> entityMap) throws DataAccessException;

	// 项目(含变更号)
	public String queryMatProjDict(Map<String, Object> entityMap) throws DataAccessException;

	// 出库物资用途
	public String queryMatOutUse(Map<String, Object> entityMap) throws DataAccessException;

	// 科室需求编制--维护供应商
	public String queryMatSupByInvId(Map<String, Object> entityMap) throws DataAccessException;

	// 领料科室
	public String queryMatAppDept(Map<String, Object> entityMap) throws DataAccessException;

	// 科室级次
	public String queryHosDeptLevel(Map<String, Object> entityMap) throws DataAccessException;

	// 查询所有仓库
	public String queryMatStoreAll(Map<String, Object> entityMap) throws DataAccessException;

	// 查询仓库(虚仓)
	public String queryMatVirStore(Map<String, Object> entityMap) throws DataAccessException;

	// 虚仓过滤库房读权限
	public String queryMatVirStoreByWrite(Map<String, Object> entityMap) throws DataAccessException;

	public String queryMatStoreStocker(Map<String, Object> entityMap) throws DataAccessException;

	// 注册证号
	public String queryMatInvCertId(Map<String, Object> entityMap) throws DataAccessException;

	// 注册证号带起止日期
	public String queryMatInvCertIdWithDate(Map<String, Object> entityMap) throws DataAccessException;

	// 管理类别
	public String queryMatManageType(Map<String, Object> entityMap) throws DataAccessException;

	// 货位
	public String queryMatLocation(Map<String, Object> entityMap) throws DataAccessException;

	// 查询带权限科室、权限仓库
	public String queryPermDeptAndStoreDict(Map<String, Object> entityMap) throws DataAccessException;

	// 查询带权限的物资分类
	public String queryPermMatTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	public String querySysUser(Map<String, Object> entityMap) throws DataAccessException;

	// 查询物资财务分类
	public String queryMatFimTypeDict(Map<String, Object> mapVo) throws DataAccessException;

	// 材料改变物资类别查询改类别材料数
	public String queryChangeMatTypeCode(Map<String, Object> mapVo) throws DataAccessException;
	
	//查询领用人
	public String queryMatEmpDictData(Map<String, Object> mapVo);
	//查询年月
	public String queryYearMonth(Map<String, Object> mapVo); 

	public String queryHosSupDictForPay(Map<String, Object> mapVo);

	String queryHosSupDictUniversalMethod(Map<String, Object> entityMap)
			throws DataAccessException;
	/**
	 * 查询对应仓库的物资分类信息
	 * @param mapVo
	 * @return
	 */
	public String queryMatTypeByStoreID(Map<String, Object> mapVo);

	String queryMatVirStoreWithEntireStoreWriteOrRead(Map<String, Object> entityMap) throws DataAccessException;

	public String queryMatExaminerEmp(Map<String, Object> mapVo) throws DataAccessException;

	String queryHosSupDictMethod(Map<String, Object> entityMap)
			throws DataAccessException;

	public String queryMatInsuraType(Map<String, Object> mapVo) throws DataAccessException;

	public String queryMatInstruType(Map<String, Object> mapVo) throws DataAccessException;

	//证件类型
	public String queryMatCertType(Map<String, Object> mapVo) throws DataAccessException;
	
	//预警类型
	public String queryMatWarnType(Map<String, Object> mapVo) throws DataAccessException;
}
