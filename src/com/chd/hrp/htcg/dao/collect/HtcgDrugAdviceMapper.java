package com.chd.hrp.htcg.dao.collect;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.collect.HtcgDrugAdvice;


public interface HtcgDrugAdviceMapper extends SqlMapper{
	
    public int addHtcgDrugAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgDrugAdvice(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<HtcgDrugAdvice> queryHtcgDrugAdvice(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgDrugAdvice> queryHtcgDrugAdvice(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcgDrugAdvice queryHtcgDrugAdviceByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgDrugAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBathcHtcgDrugAdvice(List<Map<String,Object>> list)throws DataAccessException;
	
	public int updateHtcgDrugAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
}
