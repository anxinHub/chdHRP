package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.basic.HtcMateCostRela;
/** 
* 2015-3-18 
* author:alfred
*/ 


public interface HtcMateCostRelaMapper extends SqlMapper{
	
	public int addHtcMateCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcMateCostRela(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public List<HtcMateCostRela> queryHtcMateCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcMateCostRela> queryHtcMateCostRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public HtcMateCostRela queryHtcMateCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;

	public int deleteHtcMateCostRela(Map<String,Object> entityMap)throws DataAccessException;

	public int deleteBatchHtcMateCostRela(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public int updateHtcMateCostRela(Map<String,Object> entityMap)throws DataAccessException;
}
