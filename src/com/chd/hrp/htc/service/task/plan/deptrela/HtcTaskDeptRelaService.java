package com.chd.hrp.htc.service.task.plan.deptrela;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.plan.deptrela.HtcTaskDeptRela;
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

public interface HtcTaskDeptRelaService {

public String addHtcTaskDeptRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcTaskDeptRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public HtcTaskDeptRela queryHtcTaskDeptRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcTaskDeptRela(List<Map<String, Object>> list)throws DataAccessException;
	
	public String updateHtcTaskDeptRela(Map<String,Object> entityMap)throws DataAccessException;
}
