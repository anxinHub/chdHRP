package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptBonusData;

public interface AphiDeptBonusCalculationMapper extends SqlMapper {
	
	/**
	 * 根据科室所用的计算公式得到指标 在根据指标取得 每个指标的数据
	 */
	public List<Map<String, Object>> queryDeptBonusForBonusMoney(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据科室所用的计算公式得到指标 在根据指标取得 每个指标的数据
	 */
	public List<Map<String, Object>> queryDeptBonusForBonusMoney(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * 根据科室所用的计算公式得到指标 在根据指标取得 每个指标的数据
	 */
	public List<Map<String, Object>> queryDeptBonusForBonusMoneyPrint(Map<String, Object> entityMap) throws DataAccessException;
}
