package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.basic.HtcWageItemDict;


public interface HtcWageItemDictMapper extends SqlMapper{
	
	public int addHtcWageItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addHtcWageItemMap(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 添加工资项目时，动态增加业务表的工资项目字段 
	 * @param mapList
	 */
	public void addHtcWageItemMapByAlter(Map<String, Object> entityMap);
	
	public List<HtcWageItemDict> queryHtcWageItemDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcWageItemDict> queryHtcWageItemDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcWageItemDict queryHtcWageItemDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcWageItemDict(List<Map<String, Object>> entityList)throws DataAccessException;
    
	public int deleteBatchHtcWageItemMap(List<Map<String, Object>> entityList)throws DataAccessException;

	public int updateHtcWageItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public Map<String, Object> queryHtcWageItemMapMaxId(Map<String,Object> entityMap)throws DataAccessException;

}
