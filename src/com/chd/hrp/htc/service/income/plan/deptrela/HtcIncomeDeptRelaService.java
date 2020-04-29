package com.chd.hrp.htc.service.income.plan.deptrela;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.income.plan.deptrela.HtcIncomeDeptRela;

public interface HtcIncomeDeptRelaService {
	
    public String addHtcIncomeDeptRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcIncomeDeptRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public HtcIncomeDeptRela queryHtcIncomeDeptRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcIncomeDeptRela(List<Map<String, Object>> list)throws DataAccessException;
	
	public String updateHtcIncomeDeptRela(Map<String,Object> entityMap)throws DataAccessException;
}
