package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.basic.HtcIassetCostRela;
/** 
* 2015-3-18 
* author:alfred
*/ 


public interface HtcIassetCostRelaMapper extends SqlMapper{
	
	public int addHtcIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcIassetCostRela(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public List<HtcIassetCostRela> queryHtcIassetCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcIassetCostRela> queryHtcIassetCostRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcIassetCostRela queryHtcIassetCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;

	public int deleteHtcIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcIassetCostRela(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	public int updateHtcIassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
}
