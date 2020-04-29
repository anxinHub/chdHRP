package com.chd.hrp.med.service.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.med.entity.HrpMedSelect;

public interface HrpMedSelectService {
	//是否
		public String queryMedYearOrNo(Map<String,Object> entityMap) throws DataAccessException;
		//药品类别
		public String queryMedType(Map<String,Object> entityMap) throws DataAccessException;
		//药品类别变更
		public String queryMedTypeDict(Map<String,Object> entityMap) throws DataAccessException;
		//药品类别变更
		public String queryMedTypeDictByWrite(Map<String,Object> entityMap) throws DataAccessException;
		//药品类别变更
		public String queryMedTypeDicts(Map<String,Object> entityMap) throws DataAccessException;
		
		//药品类别变更加编码
	    public String queryMedTypeDictCode(Map<String,Object> entityMap) throws DataAccessException;
			
		//药品
		public String queryMedInv(Map<String,Object> entityMap) throws DataAccessException;
		//药品变更
		public String queryMedInvDict(Map<String,Object> entityMap) throws DataAccessException;
		//财务分类
		public String queryMedFinaType(Map<String, Object> entityMap) throws DataAccessException;
		//供应商
		public String queryHosSup(Map<String, Object> entityMap) throws DataAccessException;
		//供应商变更
		public String queryHosSupDict(Map<String, Object> entityMap) throws DataAccessException;
		//供应商停用不显示
		public String queryHosSupDictDisable(Map<String, Object> entityMap) throws DataAccessException;
		//生成厂商
		public String queryHosFac(Map<String, Object> entityMap) throws DataAccessException;
		public String queryHosFacInv(Map<String, Object> entityMap) throws DataAccessException;
		//生成厂商变更
		public String queryHosFacDict(Map<String, Object> entityMap) throws DataAccessException;
		//计量单位
		public String queryHosUnit(Map<String, Object> entityMap) throws DataAccessException;
		
		//获取内置数据
		public String queryMedSysList(Map<String, Object> entityMap) throws DataAccessException;
		//货位分类
		public String queryMedLocationType(Map<String, Object> entityMap) throws DataAccessException;
		//货位字典
		public String queryMedLocationDict(Map<String, Object> entityMap) throws DataAccessException;
		//当前用户查看有权限的仓库
		public String queryMedStore(Map<String, Object> entityMap) throws DataAccessException; 
		public String queryMedStoredisable(Map<String, Object> entityMap) throws DataAccessException; 
		//当前用户查看只有读的权限的仓库
		public String queryMedStoreByRead(Map<String, Object> entityMap) throws DataAccessException;
		public String queryMedStoreByReaddisable(Map<String, Object> entityMap) throws DataAccessException;
		//当前用户查看只有写的权限的仓库
		public String queryMedStoreByWrite(Map<String, Object> entityMap) throws DataAccessException;
		//职工/领料人  --职工表
		public String queryMedEmp(Map<String, Object> entityMap) throws DataAccessException;
		//普通职工/领料人  --职工变更表
		public String queryMedEmpDict(Map<String, Object> entityMap) throws DataAccessException;
		//职能科室
		public String queryMedDeptIsManager(Map<String, Object> entityMap) throws DataAccessException;
		//库房管理员
		public String queryMedManagerEmp(Map<String, Object> entityMap) throws DataAccessException;
		//采购人
		public String queryMedStockEmp(Map<String, Object> entityMap) throws DataAccessException;
		//采购人 --变更表
		public String queryMedStockEmpDict(Map<String, Object> entityMap) throws DataAccessException;
		//采购人 一个仓库对应多个采购员 查询   即墨需求   2017/04/06  gaopei
		public String queryMedStockEmpByStore(Map<String, Object> entityMap) throws DataAccessException; 
		//库房分类
		public String queryMedStoreType(Map<String, Object> entityMap) throws DataAccessException;
		//计划状态
		public String queryMedPlanState(Map<String, Object> entityMap) throws DataAccessException;
		//编制科室
		public String queryMedDeptDict(Map<String, Object> entityMap) throws DataAccessException;
		 //通过参数控制 编制科室权限 
	    public String queryMedDeptDictDate(Map<String, Object> entityMap) throws DataAccessException;
	    
	  //通过参数控制 物资类别权限 
	    public String queryMedTypeDictDate(Map<String, Object> entityMap) throws DataAccessException;
		//通过参数控制 仓库权限 
	    public String queryMedStoreDictDate(Map<String, Object> entityMap) throws DataAccessException;
		//盘点科室
		public String queryMedPDDeptDict(Map<String, Object> entityMap) throws DataAccessException;
		//药品仓库配套表信息
		public String queryMedStoreMatch(Map<String,Object> entityMap) throws DataAccessException;
		//药品科室配套表信息
		public String queryMedDeptMatch (Map<String, Object> entityMap) throws DataAccessException;
		
		// 物资仓库配套表信息 只读
		public String queryMedStoreMatchRead(Map<String, Object> entityMap) throws DataAccessException;

		// 物资科室配套表信息 只读
		public String queryMedDeptMatchRead(Map<String, Object> entityMap) throws DataAccessException;
		//获取年份
		public String queryMedYear(Map<String, Object> entityMap) throws DataAccessException;
		//获取月份
		public String queryMedMonth(Map<String, Object> entityMap) throws DataAccessException;
		//当前用户查看有权限的科室
		public String queryMedDept(Map<String, Object> entityMap) throws DataAccessException;
		//业务类型
		public String queryMedBusType(Map<String, Object> entityMap) throws DataAccessException;
		// 业务类型
		public String queryMedBusTypes(Map<String, Object> entityMap) throws DataAccessException;

		//采购协议类别
		public String queryMedProtocolType(Map<String, Object> entityMap) throws DataAccessException;
		//当前用户查看有权限的科室并且是采购科室
		public String queryPurDept(Map<String, Object> entityMap) throws DataAccessException;
		//计划类型
		public String queryMedPlanType(Map<String, Object> entityMap) throws DataAccessException;
		//采购计划-采购员
		public String queryMedPurStockEmp(Map<String, Object> entityMap) throws DataAccessException;
		//包装单位
		public String queryMedHosPackage(Map<String, Object> entityMap) throws DataAccessException;
		//采购类型
		public String queryMedStockType(Map<String, Object> entityMap) throws DataAccessException;
		//采购协议
		public String queryMedProtocolMain(Map<String, Object> entityMap) throws DataAccessException;
		//付款方式(结算方式)
		public String queryMedPayType(Map<String, Object> entityMap) throws DataAccessException;
		//科室
		public String queryHosDept(Map<String, Object> entityMap) throws DataAccessException;
		//科室--变更
		public String queryHosDeptDict(Map<String, Object> entityMap) throws DataAccessException;
		//科室--权限
		public String queryHosDeptByPerm(Map<String, Object> entityMap) throws DataAccessException;
		//科室--变更--权限
		public String queryHosDeptDictByPerm(Map<String, Object> entityMap) throws DataAccessException;
		//查询单位带变更号
		public String queryMedHosInfoDict(Map<String, Object> entityMap) throws DataAccessException;
		//查询单位
		public String queryMedHosInfo(Map<String, Object> entityMap) throws DataAccessException;
		//根据仓库对应关系查询材料
		public String queryMedInvDictByStoreInv(Map<String, Object> entityMap) throws DataAccessException;
		//仓库(带权限) 并且带 别名
		public String queryMedStoreAlias(Map<String, Object> entityMap) throws DataAccessException;
		//材料证件分类
		public String queryMedInvCertType(Map<String, Object> entityMap) throws DataAccessException;
		//证件编码 下拉框
		public String queryMedInvCert(Map<String, Object> entityMap) throws DataAccessException;
		//供应商类别 下拉框
		public String queryHosSupType(Map<String, Object> entityMap) throws DataAccessException;
		//供应商类别 下拉框
		public String queryMedVenCertType(Map<String, Object> entityMap) throws DataAccessException;
		//药品分类级次
		public String queryMedTypeLevel(Map<String, Object> entityMap) throws DataAccessException;
		
		//药品分类级次-带id
		public String queryMedTypeLevel_2(Map<String, Object> entityMap) throws DataAccessException;
		//包装单位下拉框
		public String queryHosPackage(Map<String, Object> entityMap) throws DataAccessException;
		//签订部门：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
		public String querySignedDept(Map<String, Object> entityMap) throws DataAccessException;
		//采购发票    付款条件下拉框
		public String queryMedPayTerm(Map<String, Object> entityMap) throws DataAccessException;
		//项目下拉框
		public String queryMedProj(Map<String, Object> entityMap)throws DataAccessException;
		//项目(含变更号)
		public String queryMedProjDict(Map<String, Object> entityMap) throws DataAccessException;
		//出库药品用途
		public String queryMedOutUse(Map<String, Object> entityMap) throws DataAccessException;
		//科室需求编制--维护供应商
		public String queryMedSupByInvId(Map<String, Object> entityMap) throws DataAccessException;
		// 领料科室
		public String queryMedAppDept(Map<String, Object> entityMap) throws DataAccessException;
		// 科室级次
		public String queryHosDeptLevel(Map<String, Object> entityMap) throws DataAccessException;
		//查询所有仓库
		public String queryMedStoreAll(Map<String, Object> entityMap) throws DataAccessException;
		//查询仓库(虚仓)
		public String queryMedVirStore(Map<String, Object> entityMap) throws DataAccessException;
		//虚仓过滤库房读权限
		public String queryMedVirStoreByWrite(Map<String, Object> entityMap) throws DataAccessException;
		
		public String queryMedStoreStocker(Map<String, Object> entityMap) throws DataAccessException;
		//注册证号
		public String queryMedInvCertId(Map<String, Object> entityMap) throws DataAccessException;
		//注册证号带起止日期
		public String queryMedInvCertIdWithDate(Map<String, Object> entityMap) throws DataAccessException;
		//管理类别
		public String queryMedManageType(Map<String, Object> entityMap) throws DataAccessException;
		//货位
		public String queryMedLocation(Map<String, Object> entityMap) throws DataAccessException;
		//查询带权限科室、权限仓库
		public String queryPermDeptAndStoreDict(Map<String, Object> entityMap) throws DataAccessException;
		//查询带权限的药品分类
		public String queryPermMedTypeDict(Map<String, Object> entityMap) throws DataAccessException;
		public String querySysUser(Map<String, Object> entityMap) throws DataAccessException;
		//查询药品财务分类
		public String queryMedFimTypeDict(Map<String, Object> mapVo)throws DataAccessException;
		//材料改变药品类别查询改类别材料数
		public String queryChangeMedTypeCode(Map<String, Object> mapVo)throws DataAccessException;
		
		//药品属性
		public String queryMedSx(Map<String,Object> entityMap) throws DataAccessException;
		//药品剂型
		public String queryMedJx(Map<String,Object> entityMap) throws DataAccessException;
		
		//查询领用人
		public String queryMedEmpDictData(Map<String, Object> mapVo);
		
		
	
	
}
