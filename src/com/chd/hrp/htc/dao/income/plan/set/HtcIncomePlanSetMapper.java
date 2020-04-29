package com.chd.hrp.htc.dao.income.plan.set;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.income.plan.set.HtcIncomePlanSet;

public interface HtcIncomePlanSetMapper extends SqlMapper{
	
	public int addHtcIncomePlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcIncomePlanSet> queryHtcIncomePlanSet(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcIncomePlanSet> queryHtcIncomePlanSet(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
    public HtcIncomePlanSet queryHtcIncomePlanSetByCode(Map<String,Object> entityMap)throws DataAccessException;
    
	public int deleteBatchHtcIncomePlanSet(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateHtcIncomePlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcIncomePlanSet> queryHtcIncomePlanSetAudit(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcIncomePlanSet> queryHtcIncomePlanSetAudit(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
    public int auditHtcIncomePlanSet(List<Map<String, Object>> list)throws DataAccessException;
    
    public int cancelAuditIncomeHtcPlanSet(List<Map<String, Object>> list)throws DataAccessException;
    
    public List<HtcIncomePlanSet> queryHtcIncomePlanHistory(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcIncomePlanSet> queryHtcIncomePlanHistory(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
