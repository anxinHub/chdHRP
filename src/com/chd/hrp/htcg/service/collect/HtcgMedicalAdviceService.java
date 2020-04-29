package com.chd.hrp.htcg.service.collect;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.collect.HtcgMedicalAdvice;


public interface HtcgMedicalAdviceService {
	
	public String addHtcgMedicalAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgMedicalAdvice(List<Map<String,Object>> list)throws DataAccessException;

	public String queryHtcgMedicalAdvice(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcgMedicalAdvice queryHtcgMedicalAdviceByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcgMedicalAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgMedicalAdvice(List<Map<String,Object>> list)throws DataAccessException;
	
	public String updateHtcgMedicalAdvice(Map<String,Object> entityMap)throws DataAccessException;

}
