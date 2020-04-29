package com.chd.hrp.htcg.dao.collect;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.collect.HtcgMaterialAdvice;


public interface HtcgMaterialAdviceMapper extends SqlMapper{
	
    public int addHtcgMaterialAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgMaterialAdvice(List<Map<String,Object>> list)throws DataAccessException;

	public List< HtcgMaterialAdvice> queryHtcgMaterialAdvice(Map<String,Object> entityMap) throws DataAccessException;
	
	public List< HtcgMaterialAdvice> queryHtcgMaterialAdvice(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcgMaterialAdvice queryHtcgMaterialAdviceByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgMaterialAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgMaterialAdvice(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateHtcgMaterialAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
