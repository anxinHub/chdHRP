/*
 *
 */package com.chd.hrp.htcg.dao.collect;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.collect.HtcgIcd9Info;


public interface HtcgIcd9InfoMapper extends SqlMapper{
	
    public int addIcdHtcg9Info(Map<String,Object> entityMap)throws DataAccessException;
    
    public int addBatchIcdHtcg9Info(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<HtcgIcd9Info> queryHtcgIcd9Info(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgIcd9Info> queryHtcgIcd9Info(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcgIcd9Info queryHtcgIcd9InfoByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgIcd9Info(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgIcd9Info(List<Map<String,Object>> list)throws DataAccessException;
	
	public int updateHtcgIcd9Info(Map<String,Object> entityMap)throws DataAccessException;
	
}
