package com.chd.hrp.htcg.dao.info;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.info.HtcgAnestTypeDict;

public interface HtcgAnestTypeDictMapper extends SqlMapper{
	
	public int addHtcgAnestTypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgAnestTypeDict(List<Map<String, Object>> list)throws DataAccessException;
	
	public List<HtcgAnestTypeDict> queryHtcgAnestTypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<HtcgAnestTypeDict> queryHtcgAnestTypeDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	public HtcgAnestTypeDict queryHtcgAnestTypeDictByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgAnestTypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgAnestTypeDict(List<Map<String, Object>> list)throws DataAccessException;

	public int updateHtcgAnestTypeDict(Map<String, Object> entityMap)throws DataAccessException;
	
	
}
