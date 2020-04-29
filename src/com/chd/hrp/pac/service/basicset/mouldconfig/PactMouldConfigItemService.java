package com.chd.hrp.pac.service.basicset.mouldconfig;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface PactMouldConfigItemService {
	
	public String queryPactMouldConfig(Map<String, Object> mapVo);
	
	public String queryDataPropertySelect(Map<String, Object> mapVo);
	
	String add(Map<String, Object> mapVo);
	
	String deleteBatch(List<Map<String, Object>> listVo);
	
	String queryMouldItem(Map<String, Object> mapVo);
	
	String update(Map<String, Object> mapVo);
	
}
