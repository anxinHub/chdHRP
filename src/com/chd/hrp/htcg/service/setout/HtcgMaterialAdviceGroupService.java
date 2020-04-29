package com.chd.hrp.htcg.service.setout;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


public interface HtcgMaterialAdviceGroupService {
	
	public String initHtcgMaterialAdviceGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgMaterialAdviceGroup(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchHtcgMaterialAdviceGroup(List<Map<String,Object>> list)throws DataAccessException;
	
}
