package com.chd.hrp.hip.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hip.entity.HrpHipSelect;

public interface HrpHipSelectMapper extends SqlMapper {
	
	public List<HrpHipSelect> queryHosInfoDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<HrpHipSelect> queryHosCopy(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// 业务系统
	public List<HrpHipSelect> querySysMod(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// HIP内置视图初始化表
	public List<HrpHipSelect> queryHipInitView(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	// HIP数据源配置表
	public List<HrpHipSelect> queryHipDsCof(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// 平台科室字典
	public List<HrpHipSelect> queryHipDeptDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// 平台科室字典
	public List<HrpHipSelect> queryHipDeptDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HRP科室字典
	public List<HrpHipSelect> queryHosDeptDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HRP科室字典
	public List<HrpHipSelect> queryHosDeptDict(Map<String, Object> entityMap) throws DataAccessException;
		
	// HIP收费类别字典
	public List<HrpHipSelect> queryHipChargeKindDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;	
	
	// HRP收费类别字典
	public List<HrpHipSelect> queryCostChargeKindArrt(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HIP收费项目字典
	public List<HrpHipSelect> queryHipChargeDetailDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;	
	
	// HRP收费项目字典
	public List<HrpHipSelect> queryCostChargeItemArrt(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HIP支付方式字典
	public List<HrpHipSelect> queryHipPayTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;	
	
	// HRP支付方式字典
	public List<HrpHipSelect> queryAccPayType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HIP患者类别
	public List<HrpHipSelect> queryHipPatientTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;	
	
	// HRP患者类别
	public List<HrpHipSelect> queryHosPatientType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HIP供应商字典
	public List<HrpHipSelect> queryHipSupDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;	
	
	// HRP供应商字典变更
	public List<HrpHipSelect> queryHosSupDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HIP仓库字典
	public List<HrpHipSelect> queryHipStoreDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;	
	
	// HRP仓库字典变更
	public List<HrpHipSelect> queryHosStoreDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HRP药品分类
	public List<HrpHipSelect> queryHipMedTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HRP药品分类字典
	public List<HrpHipSelect> queryMedType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HIP药品字典
	public List<HrpHipSelect> queryHipMedDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HRP药品字典
	public List<HrpHipSelect> queryMedInv(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HIP材料分类字典
	public List<HrpHipSelect> queryHipMatTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HRP材料分类字典
	public List<HrpHipSelect> queryMatTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HIP材料字典
	public List<HrpHipSelect> queryHipMatInvDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HRP材料字典
	public List<HrpHipSelect> queryMatInvDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HIP资产分类
	public List<HrpHipSelect> queryHipAssTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
	// HRP资产分类
	public List<HrpHipSelect> queryAssTypeDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HIP资产
	public List<HrpHipSelect> queryHipAssDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
	// HRP资产
	public List<HrpHipSelect> queryAssDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HIP资金来源
	public List<HrpHipSelect> queryHipSourceDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
	// HRP资金来源
	public List<HrpHipSelect> queryHosSource(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	// HIP资金来源
	public List<HrpHipSelect> queryHipPaymentItemDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
	// HRP资金来源
	public List<HrpHipSelect> queryBudgPaymentItemDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	//	物资类别动态表头加载动态表头
	public List<Map<String,Object>> queryALLMatTypeDict(Map<String, Object> entityMap);

	public List<Map<String,Object>> queryALLMatFimTypeDict(Map<String, Object> mapVo);
	
//	药品类别动态表头加载动态表头
	public List<Map<String,Object>> queryALLMedTypeDict(Map<String, Object> entityMap);

	public List<Map<String,Object>> queryALLMedFimTypeDict(Map<String, Object> mapVo);
		

	/**
	 * 是否含同名的dblink链接
	 * @param mapVo
	 * @return
	 */
	public int existsDblink(Map<String, Object> entityMap);
	
	/**
	 * 查询平台字典对应的his视图名称
	 * @param mapVo
	 * @return
	 */
	public String queryDblinkViewName(Map<String, Object> entityMap);
	
	/**
	 * 从Dblink中取字典信息
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpHipSelect> queryDblinkDict(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 从Dblink中取字典信息
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrpHipSelect> queryDblinkDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public List<Map<String,Object>> queryHipDataSource(Map<String, Object> entityMap) throws DataAccessException;
	
}
