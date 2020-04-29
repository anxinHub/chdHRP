package com.chd.hrp.htcg.dao.setout;
import java.util.List;

import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.setout.HtcgSchemeMrSampleData;

public interface HtcgSchemeMrSampleDataMapper extends SqlMapper{
	
	public Map<String, Object>  initHtcgSchemeMrSampleData(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeMrSampleData> queryHtcgSchemeMrSampleData(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeMrSampleData> queryHtcgSchemeMrSampleData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int  deleteBatchHtcgSchemeMrSampleData(List<Map<String,Object>> list)throws DataAccessException;
	
	
}
