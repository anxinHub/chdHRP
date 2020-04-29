package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.basic.HtcMaterialTypeDict;
/** 
* 2015-3-18 
* author:alfred
*/ 

public interface HtcMaterialTypeDictMapper extends SqlMapper{
	
	public int addHtcMaterialTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcMaterialTypeDict(List<Map<String,Object>> list)throws DataAccessException;

	public List<HtcMaterialTypeDict> queryHtcMaterialTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcMaterialTypeDict> queryHtcMaterialTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcMaterialTypeDict queryHtcMaterialTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcMaterialTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcMaterialTypeDict(List<Map<String, Object>> list) throws DataAccessException;
	
	public int updateHtcMaterialTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int synchroHtcMaterialTypeDict(Map<String,Object> entityMap)throws DataAccessException;
}
