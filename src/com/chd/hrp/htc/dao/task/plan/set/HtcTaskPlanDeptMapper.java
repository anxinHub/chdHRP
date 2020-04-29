package com.chd.hrp.htc.dao.task.plan.set;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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

public interface HtcTaskPlanDeptMapper extends SqlMapper{
	
	public int addHtcTaskPlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcTaskPlanDept(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcTaskPlanDept(List<Map<String,Object>> list)throws DataAccessException;
	
	public int deleteBatchHtcTaskPlanDept(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<Map<String,Object>> queryHtcTaskPlanDeptByPlanSetOk(Map<String,Object> entityMap)throws DataAccessException;
	 
	public List<Map<String,Object>> queryHtcTaskPlanDeptByPlanSetNO(Map<String,Object> entityMap)throws DataAccessException;

}
