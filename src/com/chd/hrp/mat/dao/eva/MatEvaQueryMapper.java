package com.chd.hrp.mat.dao.eva;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatEvaQueryMapper extends SqlMapper{
	
	public List<Map<String, Object>> queryMatEvaReportMain(
			Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryMatEvaReportMain(
			Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryMatEvaReportDetail(
			Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryMatEvaReportDetail(
			Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryMatEvaSchemeTarget(
			Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryMatEvaTarget(
			Map<String, Object> mapVo) throws DataAccessException;
}
