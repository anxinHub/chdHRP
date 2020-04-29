package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.basic.HtcIassetTypeDict;
/** 
* 2015-3-18 
* author:alfred
*/ 

public interface HtcIassetTypeDictMapper extends SqlMapper{
	
	public int addHtcIassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcIassetTypeDict(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public List<HtcIassetTypeDict> queryHtcIassetTypeDict(Map<String,Object> entityMap) throws DataAccessException;

	public List<HtcIassetTypeDict> queryHtcIassetTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcIassetTypeDict queryHtcIassetTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcIassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcIassetTypeDict(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public int updateHtcIassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;
}
