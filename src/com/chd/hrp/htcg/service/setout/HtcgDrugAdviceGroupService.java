package com.chd.hrp.htcg.service.setout;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface HtcgDrugAdviceGroupService {
	
	
	
	public String initHtcgDrugAdviceGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgDrugAdviceGroup(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchHtcgDrugAdviceGroup(List<Map<String,Object>> entityMap)throws DataAccessException;
	
}
