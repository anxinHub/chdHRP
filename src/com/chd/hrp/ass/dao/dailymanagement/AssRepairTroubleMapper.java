package com.chd.hrp.ass.dao.dailymanagement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssRepairTroubleMapper extends SqlMapper {
	// 查询故障统计
	List<Map<String, Object>> queryAssRepairTrouble(
			Map<String, Object> entityMap);

	// 查询故障统计分页
	List<Map<String, Object>> queryAssRepairTrouble(
			Map<String, Object> entityMap, RowBounds rowBounds);

	// 查询费用
	List<Map<String, Object>> queryAssRepairRec(Map<String, Object> entityMap);

	// 查询费用分页
	List<Map<String, Object>> queryAssRepairRec(Map<String, Object> entityMap,
			RowBounds rowBounds);

	// 查询维修工作量
	List<Map<String, Object>> queryAssRepairHours(Map<String, Object> entityMap);

	// 查询维修工作量分页
	List<Map<String, Object>> queryAssRepairHours(
			Map<String, Object> entityMap, RowBounds rowBounds);
    //查询维修费用
	List<Map<String, Object>> queryAssRepairMoneyContrast(
			Map<String, Object> entityMap);
   //查询维修费用分页
	List<Map<String, Object>> queryAssRepairMoneyContrast(
			Map<String, Object> entityMap, RowBounds rowBounds);

}
