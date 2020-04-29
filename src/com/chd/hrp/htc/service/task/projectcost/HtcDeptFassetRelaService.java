package com.chd.hrp.htc.service.task.projectcost;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.projectcost.HtcDeptFassetRela;

public interface HtcDeptFassetRelaService {
	
	public String addHtcDeptFassetRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptFassetRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcDeptFassetRela queryHtcDeptFassetRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcDeptFassetRela(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public String updateHtcDeptFassetRela(Map<String,Object> entityMap)throws DataAccessException;
}
