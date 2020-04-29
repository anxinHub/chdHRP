package com.chd.hrp.htcg.dao.collect;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.collect.HtcgIcd10Info;


public interface HtcgIcd10InfoMapper extends SqlMapper{
	
	public int addHtcgIcd10Info(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBathcHtcgIcd10Info(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<HtcgIcd10Info> queryHtcgIcd10Info(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgIcd10Info> queryHtcgIcd10Info(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcgIcd10Info queryHtcgIcd10InfoByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgIcd10Info(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgIcd10Info(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateHtcgIcd10Info(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBathcHtcgIcd10Info(List<Map<String, Object>> list)throws DataAccessException;
}
