package com.chd.hrp.htcg.service.collect;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.collect.HtcgDrugAdvice;
public interface HtcgDrugAdviceService {

	public String addHtcgDrugAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgDrugAdvice(List<Map<String,Object>> list)throws DataAccessException;
	
	public String queryHtcgDrugAdvice(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcgDrugAdvice queryHtcgDrugAdviceByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcgDrugAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBathcHtcgDrugAdvice(List<Map<String,Object>> list)throws DataAccessException;
	
	public String updateHtcgDrugAdvice(Map<String,Object> entityMap)throws DataAccessException;

	
}
