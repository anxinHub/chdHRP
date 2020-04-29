/*
 *
 */package com.chd.hrp.htcg.dao.setout;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.setout.HtcgDrugAdviceGroup;


public interface HtcgDrugAdviceGroupMapper extends SqlMapper{
	
    public Map<String, Object> initHtcgDrugAdviceGroup(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcgDrugAdviceGroup> queryHtcgDrugAdviceGroup(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgDrugAdviceGroup> queryHtcgDrugAdviceGroup(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int deleteBatchHtcgDrugAdviceGroup(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	
}
