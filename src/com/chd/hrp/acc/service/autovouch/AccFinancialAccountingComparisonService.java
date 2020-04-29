package com.chd.hrp.acc.service.autovouch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccFinancialAccountingComparisonService {

	//财务会计科目对照查询
	String queryFinancialAccountingComparison(Map<String, Object> mapVo);

	//查询财务会计下拉框
	List<Map<String, Object>> queryFinancialAccountingComparisonSubjC(Map<String, Object> mapVo);

	//查询预算会计下拉框
	List<Map<String, Object>> queryFinancialAccountingComparisonSubjK(Map<String, Object> mapVo);

	//设置
	String addFinancialAccountingComparison(Map<String, Object> mapVo);

	//删除
	String deleteFinancialAccountingComparison(Map<String, Object> mapVo);

	//打印
	List<Map<String, Object>> queryFinancialAccountingComparisonPrint(Map<String, Object> mapVo);

	//导入
	String importFinancialAccountingComparison(Map<String, Object> mapVo);

	String updateSmartSubj(Map<String, Object> mapVo);

	//查询资金来源
	public List<Map<String, Object>> queryHosSource(Map<String, Object> mapVo) throws DataAccessException;

	//设置
	public Map<String, Object> addFinancialAccountingComparisonBySource(Map<String, Object> mapVo) throws DataAccessException;
}
