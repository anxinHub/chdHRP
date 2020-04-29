package com.chd.hrp.htcg.dao.collect;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.collect.HtcgMrInfo;


public interface HtcgMrInfoMapper extends SqlMapper{
	
    public int addHtcgMrInfo(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgMrInfo(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<HtcgMrInfo> queryHtcgMrInfo(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgMrInfo> queryHtcgMrInfo(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;

	public HtcgMrInfo queryHtcgMrInfoByCode(Map<String,Object> entityMap)throws DataAccessException;

	public int deleteHtcgMrInfo(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgMrInfo(List<Map<String,Object>> list)throws DataAccessException;
	
	public int updateHtcgMrInfo(Map<String,Object> entityMap)throws DataAccessException;

}
