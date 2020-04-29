package com.chd.hrp.htc.service.relative.plan.deptrela;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.relative.plan.deptrela.HtcRelativeDeptRela;

public interface HtcRelativeDeptRelaService {
	
    public String addHtcRelativeDeptRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcRelativeDeptRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public HtcRelativeDeptRela queryHtcRelativeDeptRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcRelativeDeptRela(List<Map<String, Object>> list)throws DataAccessException;
	
	public String updateHtcRelativeDeptRela(Map<String,Object> entityMap)throws DataAccessException;
}
