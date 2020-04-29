package com.chd.hrp.htcg.service.collect;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.collect.HtcgMaterialAdvice;


public interface HtcgMaterialAdviceService {
	
	public String addHtcgMaterialAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgMaterialAdvice(List<Map<String,Object>> list)throws DataAccessException;

	public String queryHtcgMaterialAdvice(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcgMaterialAdvice queryHtcgMaterialAdviceByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcgMaterialAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgMaterialAdvice(List<Map<String, Object>> list)throws DataAccessException;
	
	public String updateHtcgMaterialAdvice(Map<String,Object> entityMap)throws DataAccessException;

	
}
