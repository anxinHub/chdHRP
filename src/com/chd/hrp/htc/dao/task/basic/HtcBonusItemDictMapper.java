package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.basic.HtcBonusItemDict;
/**
 * 2015-3-18 author:alfred
 */


public interface HtcBonusItemDictMapper extends SqlMapper{
	
	public int addHtcBonusItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addHtcBonusItemMap(Map<String,Object> entityMap)throws DataAccessException;
	
	public void addHtcBonusItemMapByAlter(Map<String, Object> entityMap);
	
	public List<HtcBonusItemDict> queryHtcBonusItemDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcBonusItemDict> queryHtcBonusItemDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcBonusItemDict queryHtcBonusItemDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcBonusItemDict(List<Map<String, Object>> entityList)throws DataAccessException;
    
    public int deleteBatchHtcBonusItemMap(List<Map<String, Object>> entityList) throws DataAccessException;
    
	public int updateHtcBonusItemDict(Map<String,Object> entityMap)throws DataAccessException;

	public Map<String, Object> queryHtcBonusItemMapMaxId(Map<String, Object> entityMap)throws DataAccessException;

}
