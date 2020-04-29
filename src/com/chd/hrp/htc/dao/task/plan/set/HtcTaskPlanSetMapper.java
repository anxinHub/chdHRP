package com.chd.hrp.htc.dao.task.plan.set;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.plan.set.HtcTaskPlanSet;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcTaskPlanSetMapper extends SqlMapper{
	
    public int addHtcTaskPlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcTaskPlanSet> queryHtcTaskPlanSet(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcTaskPlanSet> queryHtcTaskPlanSet(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
    public HtcTaskPlanSet queryHtcTaskPlanSetByCode(Map<String,Object> entityMap)throws DataAccessException;
    
	public int deleteBatchHtcTaskPlanSet(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateHtcTaskPlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcTaskPlanSet> queryHtcTaskPlanSetAudit(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcTaskPlanSet> queryHtcTaskPlanSetAudit(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
    public int auditHtcTaskPlanSet(List<Map<String, Object>> list)throws DataAccessException;
    
    public int cancelAuditHtcTaskPlanSet(List<Map<String, Object>> list)throws DataAccessException;
    
    public List<HtcTaskPlanSet> queryHtcTaskPlanHistory(Map<String,Object> entityMap)throws DataAccessException;
    
    public List<HtcTaskPlanSet> queryHtcTaskPlanHistory(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	
}
