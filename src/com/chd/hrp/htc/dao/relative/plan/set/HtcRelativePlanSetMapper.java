package com.chd.hrp.htc.dao.relative.plan.set;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.relative.plan.set.HtcRelativePlanSet;

public interface HtcRelativePlanSetMapper extends SqlMapper{
	
	public int addHtcRelativePlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcRelativePlanSet> queryHtcRelativePlanSet(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcRelativePlanSet> queryHtcRelativePlanSet(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
    public HtcRelativePlanSet queryHtcRelativePlanSetByCode(Map<String,Object> entityMap)throws DataAccessException;
    
	public int deleteBatchHtcRelativePlanSet(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateHtcRelativePlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcRelativePlanSet> queryHtcRelativePlanSetAudit(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcRelativePlanSet> queryHtcRelativePlanSetAudit(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
    public int auditHtcRelativePlanSet(List<Map<String, Object>> list)throws DataAccessException;
    
    public int cancelAuditHtcRelativePlanSet(List<Map<String, Object>> list)throws DataAccessException;
    
    public List<HtcRelativePlanSet> queryHtcRelativePlanHistory(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcRelativePlanSet> queryHtcRelativePlanHistory(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
