package com.chd.hrp.htcg.dao.collect;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.collect.HtcgMedicalAdvice;


public interface HtcgMedicalAdviceMapper extends SqlMapper{
	
    public int addHtcgMedicalAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgMedicalAdvice(List<Map<String,Object>> list)throws DataAccessException;

	public List<HtcgMedicalAdvice> queryHtcgMedicalAdvice(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgMedicalAdvice> queryHtcgMedicalAdvice(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcgMedicalAdvice queryHtcgMedicalAdviceByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgMedicalAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgMedicalAdvice(List<Map<String,Object>> list)throws DataAccessException;
	
	public int updateHtcgMedicalAdvice(Map<String,Object> entityMap)throws DataAccessException;
	
}
