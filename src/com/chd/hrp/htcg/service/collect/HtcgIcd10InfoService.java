package com.chd.hrp.htcg.service.collect;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.collect.HtcgIcd10Info;


public interface HtcgIcd10InfoService {
	
    public String addHtcgIcd10Info(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBathcHtcgIcd10Info(List<Map<String,Object>> list)throws DataAccessException;
	
	public String queryHtcgIcd10Info(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcgIcd10Info queryHtcgIcd10InfoByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcgIcd10Info(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgIcd10Info(List<Map<String, Object>> list)throws DataAccessException;
	
	public String updateHtcgIcd10Info(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
