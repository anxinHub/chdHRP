package com.chd.hrp.htc.dao.task.projectcost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptIassetRela;

public interface HtcDeptIassetRelaMapper extends SqlMapper{

	public int addHtcDeptIassetRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptIassetRela> queryHtcDeptIassetRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptIassetRela> queryHtcDeptIassetRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcDeptIassetRela queryHtcDeptIassetRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public  int deleteBatchHtcDeptIassetRela(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public  int updateHtcDeptIassetRela(Map<String,Object> entityMap)throws DataAccessException;
}
