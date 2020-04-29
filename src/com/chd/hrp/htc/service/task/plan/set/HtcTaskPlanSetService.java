package com.chd.hrp.htc.service.task.plan.set;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

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

public interface HtcTaskPlanSetService {

    public String addHtcTaskPlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcTaskPlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
	public HtcTaskPlanSet queryHtcTaskPlanSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcTaskPlanSet(List<Map<String, Object>> list)throws DataAccessException;
	
	public String updateHtcTaskPlanSet(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String queryHtcTaskPlanSetAudit(Map<String,Object> entityMap)throws DataAccessException;
	
	public String auditHtcTaskPlanSet(List<Map<String, Object>> list)throws DataAccessException;
	
	public String cancelAuditHtcTaskPlanSet(List<Map<String, Object>> list)throws DataAccessException;
	
	public String queryHtcTaskPlanHistory(Map<String,Object> entityMap)throws DataAccessException;
}
