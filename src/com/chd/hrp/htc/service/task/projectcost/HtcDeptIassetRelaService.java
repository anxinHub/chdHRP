package com.chd.hrp.htc.service.task.projectcost;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.projectcost.HtcDeptIassetRela;

public interface HtcDeptIassetRelaService {

	public String addHtcDeptIassetRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptIassetRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcDeptIassetRela queryHtcDeptIassetRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcDeptIassetRela(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public String updateHtcDeptIassetRela(Map<String,Object> entityMap)throws DataAccessException;
}
