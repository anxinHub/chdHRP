package com.chd.hrp.ass.service.guanLiReports;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;

public interface AssDeptScrapService extends SqlService{
	public List<Map<String, Object>> queryAssDeptScrapSummryPrint(
			Map<String, Object> entityMap);
	
	public String queryAssDeptScrapSummry(
			Map<String, Object> entityMap);
}
