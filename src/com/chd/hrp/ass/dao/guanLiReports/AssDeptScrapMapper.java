package com.chd.hrp.ass.dao.guanLiReports;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssDeptScrapMapper extends SqlMapper{
	public List<Map<String, Object>> queryAssDeptScrapSummryPrint(
			Map<String, Object> entityMap);
	
	public List<Map<String, Object>> queryAssDeptScrapSummry(
			Map<String, Object> entityMap);
	
	public List<Map<String, Object>> queryAssDeptScrapSummry(
			Map<String, Object> entityMap, RowBounds rowBounds);
}
