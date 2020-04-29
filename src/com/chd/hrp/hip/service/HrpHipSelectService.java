package com.chd.hrp.hip.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.hip.entity.HrpHipSelect;

public interface HrpHipSelectService {
	
	public String queryHosInfoDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryHosCopy(Map<String, Object> entityMap) throws DataAccessException;

	// 业务系统
	public String querySysMod(Map<String, Object> entityMap) throws DataAccessException;

	// HIP内置视图初始化表
	public String queryHipInitView(Map<String, Object> entityMap) throws DataAccessException;

	// HIP数据源配置表
	public String queryHipDsCof(Map<String, Object> entityMap) throws DataAccessException;

	// 平台科室字典
	public String queryHipDeptDict(Map<String, Object> entityMap) throws DataAccessException;

	// HRP科室字典
	public String queryHosDeptDict(Map<String, Object> entityMap) throws DataAccessException;

	// HIP收费类别
	public String queryHipChargeKindDict(Map<String, Object> entityMap) throws DataAccessException;

	// HRP收费类别字典
	public String queryCostChargeKindArrt(Map<String, Object> entityMap) throws DataAccessException;

	// HIP收费项目字典
	public String queryHipChargeDetailDict(Map<String, Object> entityMap) throws DataAccessException;

	// HRP收费项目字典
	public String queryCostChargeItemArrt(Map<String, Object> entityMap) throws DataAccessException;

	// HIP支付方式字典
	public String queryHipPayTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	// HRP支付方式字典
	public String queryAccPayType(Map<String, Object> entityMap) throws DataAccessException;

	// HIP患者类别
	public String queryHipPatientTypeDict(Map<String, Object> entityMap) throws DataAccessException;

	// HRP患者类别
	public String queryHosPatientType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 供应商字典
	public String queryHipSupDict(Map<String, Object> entityMap) throws DataAccessException;	
	
	// HRP供应商字典变更
	public String queryHosSupDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// HIP仓库字典
	public String queryHipStoreDict(Map<String, Object> entityMap) throws DataAccessException;	
	
	// HRP仓库字典变更
	public String queryHosStoreDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// HRP药品分类
	public String queryHipMedTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// HRP药品分类字典
	public String queryMedType(Map<String, Object> entityMap) throws DataAccessException;
	
	// HRP药品分类
	public String queryHipMedDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// HRP药品分类字典
	public String queryMedInv(Map<String, Object> entityMap) throws DataAccessException;
	
	// HIP材料分类字典
	public String queryHipMatTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// HRP材料分类字典
	public String queryMatTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// HIP材料字典
	public String queryHipMatInvDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// HRP材料字典
	public String queryMatInvDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// HIP资产分类
	public String queryHipAssTypeDict(Map<String, Object> entityMap) throws DataAccessException;
		
	// HRP资产分类
	public String queryAssTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// HIP资产
	public String queryHipAssDict(Map<String, Object> entityMap) throws DataAccessException;
		
	// HRP资产
	public String queryAssDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// HIP资金来源
	public String queryHipSourceDict(Map<String, Object> entityMap) throws DataAccessException;
		
	// HRP资金来源
	public String queryHosSource(Map<String, Object> entityMap) throws DataAccessException;
	
	// HIP资金来源
	public String queryHipPaymentItemDict(Map<String, Object> entityMap) throws DataAccessException;
		
	// HRP资金来源
	public String queryBudgPaymentItemDict(Map<String, Object> entityMap) throws DataAccessException;

	public String queryALLMatTypeDict(Map<String, Object> mapVo) throws DataAccessException;

	public String queryALLMatFimTypeDict(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryALLMedTypeDict(Map<String, Object> mapVo) throws DataAccessException;

	public String queryALLMedFimTypeDict(Map<String, Object> mapVo) throws DataAccessException;

	public String queryHipDataSource(Map<String, Object> mapVo) throws DataAccessException;

}
