package com.chd.hrp.acc.dao.autovouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AccFinancialAccountingComparisonMapper extends SqlMapper{

	//查询
	List<Map<String, Object>> queryFinancialAccountingComparison(
			Map<String, Object> mapVo);
	List<Map<String, Object>> queryFinancialAccountingComparison(
			Map<String, Object> mapVo, RowBounds rowBounds);
	
	//查询财务会计科目
	List<Map<String, Object>> queryFinancialAccountingComparisonSubjC(
			Map<String, Object> mapVo);
	
	//查询预算会计科目
	List<Map<String, Object>> queryFinancialAccountingComparisonSubjK(
			Map<String, Object> mapVo);
	
	//删除
	int deleteFinancialAccountingComparison(Map<String, Object> mapVo);
	
	//添加
	int addFinancialAccountingComparison(Map<String, Object> mapVo);
	int addBatchFinancialAccountingComparison(@Param(value="map")Map<String, Object> mapVo, @Param(value="list")List<Map<String, Object>> list);
	
	//查询已经存在的Code
	List<String> queryFinancialSubjCode(Map<String, Object> mapVo);
	
	//导入
	int importFinancialAccountingComparison(@Param("list")List<Map> addList,
			@Param("vo")Map<String, Object> mapVo);

	
	int updateSmartSubj(Map<String, Object> mapVo);
	
	//导入前删除
	void deleteimportFinancial(@Param("list")List<Map> addList, @Param("vo")Map<String, Object> mapVo);
	
	//查询重复编码
	List<String> queryFinancialCode(Map<String, Object> mapVo);
	
	//查询资金来源
	public List<Map<String, Object>> queryHosSource(Map<String, Object> mapVo) throws DataAccessException;
	
	//查询资金来源的check_type_id
	public int getSourceCheckId(Map<String, Object> mapVo) throws DataAccessException;
}
