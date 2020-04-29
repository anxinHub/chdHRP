package com.chd.hrp.htcg.service.setout;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.setout.HtcgMedicalAdviceGroup;


public interface HtcgMedicalAdviceGroupService {
	
	
	public String initHtcgMedicalAdviceGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgMedicalAdviceGroup(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchHtcgMedicalAdviceGroup(List<Map<String,Object>> list)throws DataAccessException;
	
}
