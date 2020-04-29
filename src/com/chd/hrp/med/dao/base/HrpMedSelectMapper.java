package com.chd.hrp.med.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.HrpMedSelect;

public interface HrpMedSelectMapper extends SqlMapper{
	//是否
		public List<HrpMedSelect> queryMedYearOrNo(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//药品类别
		public List<HrpMedSelect> queryMedType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//药品类别变更
		public List<HrpMedSelect> queryMedTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		public List<HrpMedSelect> queryMedTypeDictByWrite(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//药品类别变更
		public List<HrpMedSelect> queryMedTypeDicts(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//药品类别变更
	    public List<HrpMedSelect> queryMedTypeDictCode(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//财务分类
		public List<HrpMedSelect> queryMedFinaType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//供应商
		public List<HrpMedSelect> queryHosSup(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//供应商变更
		public List<HrpMedSelect> queryHosSupDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

		public List<HrpMedSelect> queryHosSupDictDisable(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
		//生产厂商
		public List<HrpMedSelect> queryHosFac(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//生产厂商
		public List<HrpMedSelect> queryHosFacInv(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//生产厂商变更
		public List<HrpMedSelect> queryHosFacDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//药品
		public List<HrpMedSelect> queryMedInv(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//药品变更
		public List<HrpMedSelect> queryMedInvDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//计量单位
		public List<HrpMedSelect> queryHosUnit(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
		//获取内置数据
		public List<HrpMedSelect> queryMedSysList(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//获取货位分类
		public List<HrpMedSelect> queryMedLocationType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//获取货位字典
		public List<HrpMedSelect> queryMedLocationDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//当前用户查看有权限的仓库
		public List<HrpMedSelect> queryMedStore(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		
		public List<HrpMedSelect> queryMedStoredisable(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//当前用户查看只有读的权限的仓库
		public List<HrpMedSelect> queryMedStoreByRead(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		public List<HrpMedSelect> queryMedStoreByReaddisable(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//当前用户查看只有写的权限的仓库
		public List<HrpMedSelect> queryMedStoreByWrite(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	    
		//职工、领料人 
		public List<HrpMedSelect> queryMedEmp(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//职工、领料人   --变更表
		public List<HrpMedSelect> queryMedEmpDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		// 材料证件分类
		public List<HrpMedSelect> qryMedInvCertType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//职能科室
		public List<HrpMedSelect> queryMedDeptIsManager(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//库房管理员
		public List<HrpMedSelect> queryMedManagerEmp(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//采购人
		public List<HrpMedSelect> queryMedStockEmp(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//采购人--变更表
		public List<HrpMedSelect> queryMedStockEmpDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//采购人 一个仓库对应多个采购员 查询  即墨需求   2017/04/06  gaopei
	    public List<HrpMedSelect> queryMedStockEmpByStore(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//库房分类
		public List<HrpMedSelect> queryMedStoreType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//计划状态
		public List<HrpMedSelect> queryMedPlanState(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//编制科室
		public List<HrpMedSelect> queryMedDeptDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
		//通过参数控制 编制科室权限 
		public List<HrpMedSelect> queryMedDeptDictDate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//通过参数控制 物资类别权限 
		public List<HrpMedSelect> queryMedTypeDictDate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
		//通过参数控制 仓库权限 
		public List<HrpMedSelect> queryMedStoreDictDate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//盘点科室
		public List<HrpMedSelect> queryMedPDDeptDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		
		//药品仓库配套表信息
		public List<HrpMedSelect> queryMedStoreMatch(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;	
		//药品科室配套表信息
		public List<HrpMedSelect> queryMedDeptMatch (Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
		
		//物资仓库配套表信息 只读
		public List<HrpMedSelect> queryMedStoreMatchRead(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;	
		//物资科室配套表信息 只读
		public List<HrpMedSelect> queryMedDeptMatchRead (Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
		//获取年份
		public List<HrpMedSelect> queryMedYear(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//获取月份
		public List<HrpMedSelect> queryMedMonth(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//证件编码 下拉框
		public List<HrpMedSelect> queryMedInvCert(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
		//供应商类别 下拉框
		public List<HrpMedSelect> queryHosSupType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
		//供应商 证件类别
		public List<HrpMedSelect> queryMedVenCertType(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
		//当前用户查看有权限的科室
		public List<HrpMedSelect> queryMedDept(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
		//当前用户查看有权限的科室
		public List<HrpMedSelect> queryMedStoreAlias(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

		//业务类型
		public List<HrpMedSelect> queryMedBusType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
		//药品分类级次
		public List<HrpMedSelect> queryMedTypeLevel(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
		
		//药品分类级次-带id
		public List<HrpMedSelect> queryMedTypeLevel_2(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
		//采购协议类别
		public List<HrpMedSelect> queryMedProtocolType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
		//包装单位下拉框
		public List<HrpMedSelect> queryHosPackage(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//当前用户查看有权限的科室并且是采购科室
		public List<HrpMedSelect> queryPurDept(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
		//计划类型
		public List<HrpMedSelect> queryMedPlanType(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
		//采购计划-采购员
		public List<HrpMedSelect> queryMedPurStockEmp(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
		//包装单位
		public List<HrpMedSelect> queryMedHosPackage(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
		//签订部门：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
		public List<HrpMedSelect> querySignedDept(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//采购类型
		public List<HrpMedSelect> queryMedStockType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//采购协议
		public List<HrpMedSelect> queryMedProtocolMain(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//付款方式(结算方式)
		public List<HrpMedSelect> queryMedPayType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//采购发票 付款条件下拉框
		public List<HrpMedSelect> queryMedPayTerm(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//科室
		public List<HrpMedSelect> queryHosDept(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//科室--变更
		public List<HrpMedSelect> queryHosDeptDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//有权限科室
		public List<HrpMedSelect> queryHosDeptByPerm(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//有权限科室--变更
		public List<HrpMedSelect> queryHosDeptDictByPerm(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//查询单位
		public List<HrpMedSelect> queryMedHosInfoDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//查询单位
		public List<HrpMedSelect> queryMedHosInfo(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//根据仓库对应关系查询材料
		public List<HrpMedSelect> queryMedInvDictByStoreInv(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//项目下拉框
		public List<HrpMedSelect> queryMedProj(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//项目(含变更号)
		public List<HrpMedSelect> queryMedProjDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//出库药品用途
		public List<HrpMedSelect> queryMedOutUse(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//科室需求计划编制--维护供应商
		public List<HrpMedSelect> queryMedSupByInvId(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//领料科室
		public List<HrpMedSelect> queryMedAppDept(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//科室级次
		public List<HrpMedSelect> queryHosDeptLevel(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//查询所有仓库
		public List<HrpMedSelect> queryMedStoreAll(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//查询仓库(虚仓)
		public List<HrpMedSelect> queryMedVirStore(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//虚仓过滤库房读权限
		public List<HrpMedSelect> queryMedVirStoreByWrite(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		
		public List<HrpMedSelect> queryMedStoreStocker(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//注册证号
		public List<HrpMedSelect> queryMedInvCertId(Map<String, Object> entityMap) throws DataAccessException;
		//注册证号带分页
		public List<HrpMedSelect> queryMedInvCertId(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//注册证号 带起止日期
		public List<HrpMedSelect> queryMedInvCertIdWithDate(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//管理类别
		public List<HrpMedSelect> queryMedManageType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//货位
		public List<HrpMedSelect> queryMedLocation(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//查询带权限科室、权限仓库
		public List<HrpMedSelect> queryPermDeptAndStoreDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//查询带权限的药品分类
		public List<HrpMedSelect> queryPermMedTypeDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//查询制单人
	    public List<HrpMedSelect> querySysUser(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
		//查询药品财务分类
	    public List<HrpMedSelect> queryMedFimTypeDict(Map<String, Object> mapVo,RowBounds rowBounds)throws DataAccessException;
		//材料改变药品类别查询改类别材料数
	    public List<HrpMedSelect> queryChangeMedTypeCode(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	    
	    //药品属性
		public List<HrpMedSelect> queryMedSx(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		//药品剂型
		public List<HrpMedSelect> queryMedJx(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
		//查询领用人
	    public List<Map<String, Object>> queryMedEmpDictData(Map<String, Object> mapVo,RowBounds rowBounds)throws DataAccessException;
		
	    public List<HrpMedSelect> queryMedBusTypes(Map<String, Object> entityMap,
				RowBounds rowBounds) throws DataAccessException;
	
		
}
