package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.basic.HtcMaterialDict;
/** 
* 2015-3-18 
* author:alfred
*/ 

public interface HtcMaterialDictMapper extends SqlMapper{
	
	public int addHtcMaterialDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcMaterialDict(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public List<HtcMaterialDict> queryHtcMaterialDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcMaterialDict> queryHtcMaterialDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public HtcMaterialDict queryHtcMaterialDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcMaterialDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcMaterialDict(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public int updateHtcMaterialDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int synchroHtcMaterialDict(Map<String,Object> entityMap)throws DataAccessException;
}
