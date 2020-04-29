package com.chd.hrp.htcg.dao.audit;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.audit.HtcgSchemeConf;

public interface HtcgSchemeConfMapper extends SqlMapper{

	public int addBatchHtcgSchemeConf(List<Map<String, Object>> list) throws DataAccessException;
	
	public List<HtcgSchemeConf> queryHtcgSchemeConf(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeConf> queryHtcgSchemeConf(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryHtcgSchemeConfPeriod(Map<String, Object> entityMap) throws DataAccessException;
	
	public int deleteHtcgSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	public int deleteBatchHtcgSchemeConf(List<Map<String, Object>> list) throws DataAccessException;
	

}
