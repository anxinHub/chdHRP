package com.chd.hrp.mat.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.mat.entity.HrpMatSelect;

public interface HrpMatSelectMapper extends SqlMapper{
	//是否
	public List<HrpMatSelect> queryMatYearOrNo(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//物资类别
	public List<HrpMatSelect> queryMatType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//物资类别变更
	public List<HrpMatSelect> queryMatTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<HrpMatSelect> queryMatTypeDictByWrite(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//物资类别变更
	public List<HrpMatSelect> queryMatTypeDicts(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//物资类别变更
    public List<HrpMatSelect> queryMatTypeDictCode(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//财务分类
	public List<HrpMatSelect> queryMatFinaType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//供应商
	public List<HrpMatSelect> queryHosSup(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//供应商变更
	public List<HrpMatSelect> queryHosSupDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<HrpMatSelect> queryHosSupDictDisable(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<HrpMatSelect> queryHosSupDictUniversalMethod(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//生产厂商
	public List<HrpMatSelect> queryHosFac(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//生产厂商
	public List<HrpMatSelect> queryHosFacInv(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//生产厂商变更
	public List<HrpMatSelect> queryHosFacDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//物资
	public List<HrpMatSelect> queryMatInv(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//物资变更
	public List<HrpMatSelect> queryMatInvDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//计量单位
	public List<HrpMatSelect> queryHosUnit(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//获取内置数据
	public List<HrpMatSelect> queryMatSysList(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//获取货位分类
	public List<HrpMatSelect> queryMatLocationType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//获取货位字典
	public List<HrpMatSelect> queryMatLocationDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//当前用户查看有权限的仓库
	public List<HrpMatSelect> queryMatStore(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	public List<HrpMatSelect> queryMatStoredisable(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//当前用户查看只有读的权限的仓库
	public List<HrpMatSelect> queryMatStoreByRead(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	public List<HrpMatSelect> queryMatStoreByReaddisable(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//当前用户查看只有写的权限的仓库
	public List<HrpMatSelect> queryMatStoreByWrite(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
    
	//职工、领料人 
	public List<HrpMatSelect> queryMatEmp(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//职工、领料人   --变更表
	public List<HrpMatSelect> queryMatEmpDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	// 材料证件分类
	public List<HrpMatSelect> qryMatInvCertType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//职能科室
	public List<HrpMatSelect> queryMatDeptIsManager(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//库房管理员
	public List<HrpMatSelect> queryMatManagerEmp(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//采购人
	public List<HrpMatSelect> queryMatStockEmp(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//采购人--变更表
	public List<HrpMatSelect> queryMatStockEmpDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//采购人 一个仓库对应多个采购员 查询  即墨需求   2017/04/06  gaopei
    public List<HrpMatSelect> queryMatStockEmpByStore(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//库房分类
	public List<HrpMatSelect> queryMatStoreType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//计划状态
	public List<HrpMatSelect> queryMatPlanState(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//编制科室
	public List<HrpMatSelect> queryMatDeptDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//通过参数控制 编制科室权限 
	public List<HrpMatSelect> queryMatDeptDictDate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//通过参数控制 物资类别权限 
	public List<HrpMatSelect> queryMatTypeDictDate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//通过参数控制 仓库权限 
	public List<HrpMatSelect> queryMatStoreDictDate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    //库房处只能选到‘是否采购库房’为是的仓库
	public List<HrpMatSelect> queryMatStoreDictPro(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//盘点科室 
	public List<HrpMatSelect> queryMatPDDeptDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	//物资仓库配套表信息
	public List<HrpMatSelect> queryMatStoreMatch(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;	
	//物资科室配套表信息
	public List<HrpMatSelect> queryMatDeptMatch (Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	//物资仓库配套表信息 只读
	public List<HrpMatSelect> queryMatStoreMatchRead(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;	
	//物资科室配套表信息 只读
	public List<HrpMatSelect> queryMatDeptMatchRead (Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	//获取年份
	public List<HrpMatSelect> queryMatYear(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//获取月份
	public List<HrpMatSelect> queryMatMonth(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//证件编码 下拉框
	public List<HrpMatSelect> queryMatInvCert(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	//供应商类别 下拉框
	public List<HrpMatSelect> queryHosSupType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	//供应商 证件类别
	public List<HrpMatSelect> queryMatVenCertType(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	//当前用户查看有权限的科室
	public List<HrpMatSelect> queryMatDept(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	//当前用户查看有权限的科室
	public List<HrpMatSelect> queryMatStoreAlias(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	//业务类型
	public List<HrpMatSelect> queryMatBusType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	//物资分类级次
	public List<HrpMatSelect> queryMatTypeLevel(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	
	//物资分类级次-带id
	public List<HrpMatSelect> queryMatTypeLevel_2(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	//采购协议类别
	public List<HrpMatSelect> queryMatProtocolType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	//包装单位下拉框
	public List<HrpMatSelect> queryHosPackage(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//当前用户查看有权限的科室并且是采购科室
	public List<HrpMatSelect> queryPurDept(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	//计划类型
	public List<HrpMatSelect> queryMatPlanType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	//采购计划-采购员
	public List<HrpMatSelect> queryMatPurStockEmp(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	//包装单位
	public List<HrpMatSelect> queryMatHosPackage(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	//签订部门：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
	public List<HrpMatSelect> querySignedDept(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//采购类型
	public List<HrpMatSelect> queryMatStockType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//采购协议
	public List<HrpMatSelect> queryMatProtocolMain(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//付款协议
	public List<HrpMatSelect> queryMatPactFkxyMain(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//付款方式(结算方式)
	public List<HrpMatSelect> queryMatPayType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//采购发票 付款条件下拉框
	public List<HrpMatSelect> queryMatPayTerm(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//科室
	public List<HrpMatSelect> queryHosDept(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//科室--变更
	public List<HrpMatSelect> queryHosDeptDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//有权限科室
	public List<HrpMatSelect> queryHosDeptByPerm(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//有权限科室--变更
	public List<HrpMatSelect> queryHosDeptDictByPerm(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//查询单位
	public List<HrpMatSelect> queryMatHosInfoDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//查询单位
	public List<HrpMatSelect> queryMatHosInfo(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//根据仓库对应关系查询材料
	public List<HrpMatSelect> queryMatInvDictByStoreInv(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//项目下拉框
	public List<HrpMatSelect> queryMatProj(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//项目(含变更号)
	public List<HrpMatSelect> queryMatProjDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//出库物资用途
	public List<HrpMatSelect> queryMatOutUse(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//科室需求计划编制--维护供应商
	public List<HrpMatSelect> queryMatSupByInvId(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//领料科室
	public List<HrpMatSelect> queryMatAppDept(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//科室级次
	public List<HrpMatSelect> queryHosDeptLevel(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	//查询所有仓库
	public List<HrpMatSelect> queryMatStoreAll(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//查询仓库(虚仓)
	public List<HrpMatSelect> queryMatVirStore(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//虚仓过滤库房读权限
	public List<HrpMatSelect> queryMatVirStoreByWrite(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	// 只要有一个实仓没有读写权限,该虚仓都不 显示
	public List<HrpMatSelect> queryMatVirStoreWithEntireStoreWriteOrRead(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	public List<HrpMatSelect> queryMatStoreStocker(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//注册证号
	public List<HrpMatSelect> queryMatInvCertId(Map<String, Object> entityMap) throws DataAccessException;
	//注册证号带分页
	public List<HrpMatSelect> queryMatInvCertId(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//注册证号 带起止日期
	public List<HrpMatSelect> queryMatInvCertIdWithDate(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//管理类别
	public List<HrpMatSelect> queryMatManageType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//货位
	public List<HrpMatSelect> queryMatLocation(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//查询带权限科室、权限仓库
	public List<HrpMatSelect> queryPermDeptAndStoreDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//查询带权限的物资分类  
	public List<HrpMatSelect> queryPermMatTypeDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//查询制单人  
    public List<HrpMatSelect> querySysUser(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	//查询物资财务分类
    public List<HrpMatSelect> queryMatFimTypeDict(Map<String, Object> mapVo,RowBounds rowBounds)throws DataAccessException;
	//材料改变物资类别查询改类别材料数
    public List<HrpMatSelect> queryChangeMatTypeCode(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	//查询领用人
    public List<Map<String, Object>> queryMatEmpDictData(Map<String, Object> mapVo,RowBounds rowBounds)throws DataAccessException;
	
    public List<HrpMatSelect> queryMatBusTypes(Map<String, Object> entityMap,
			RowBounds rowBounds) throws DataAccessException;
    //查询年月
	public List<Map<String, Object>> queryYearMonth(Map<String, Object> mapVo);
	//查供应商(包括发生过付款业务停用的供应商)
	public List<HrpMatSelect> queryHosSupDictForPay(Map<String, Object> entityMap,
			RowBounds rowBounds);
	public List<HrpMatSelect> queryMatPayType(Map<String, Object> entityMap); 
	//通过仓库id,查询对应仓库的物资材料信息
	public List<HrpMatSelect> queryMatTypeByStoreID(Map<String, Object> mapVo);
	public List<HrpMatSelect> queryMatExaminerEmp(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	public List<HrpMatSelect> queryHosSupDictMethod(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	// 医疗保险分类
	public List<HrpMatSelect> queryMatInsuraType(Map<String, Object> mapVo,
			RowBounds rowBounds) throws DataAccessException;
	//医疗器械分类
	public List<HrpMatSelect> queryMatInstruType(Map<String, Object> mapVo,
			RowBounds rowBounds) throws DataAccessException;
	
	//证件类型
	public List<HrpMatSelect> queryMatCertType(Map<String, Object> mapVo) throws DataAccessException;
	
	//预警类型
	public List<HrpMatSelect> queryMatWarnType(Map<String, Object> mapVo)throws DataAccessException;
}

