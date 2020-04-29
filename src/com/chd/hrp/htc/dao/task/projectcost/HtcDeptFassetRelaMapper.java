package com.chd.hrp.htc.dao.task.projectcost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.projectcost.HtcDeptFassetRela;

public interface HtcDeptFassetRelaMapper extends SqlMapper{

	public int addHtcDeptFassetRela(Map<String,Object> entityMap)throws DataAccessException;
		
	public List<HtcDeptFassetRela> queryHtcDeptFassetRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptFassetRela> queryHtcDeptFassetRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcDeptFassetRela queryHtcDeptFassetRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public  int deleteBatchHtcDeptFassetRela(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public  int updateHtcDeptFassetRela(Map<String,Object> entityMap)throws DataAccessException;
}
