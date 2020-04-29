package com.chd.hrp.htcg.dao.info;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.info.HtcgIcd10TypeDict;

public interface HtcgIcd10TypeDictMapper extends SqlMapper{
	
	
    public int addHtcgIcd10TypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgIcd10TypeDict(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<HtcgIcd10TypeDict> queryHtcgIcd10TypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<HtcgIcd10TypeDict> queryHtcgIcd10TypeDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	public HtcgIcd10TypeDict queryHtcgIcd10TypeDictDicByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgIcd10TypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgIcd10TypeDict(List<Map<String,Object>> entityList)throws DataAccessException;

	public int updateHtcgIcd10TypeDict(Map<String, Object> entityMap)throws DataAccessException;
 
	
}
