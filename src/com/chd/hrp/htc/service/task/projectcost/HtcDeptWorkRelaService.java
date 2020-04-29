package com.chd.hrp.htc.service.task.projectcost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.projectcost.HtcDeptWorkRela;
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

public interface HtcDeptWorkRelaService {

	
	public String addHtcDeptWorkRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptWorkRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcDeptWorkRela queryHtcDeptWorkRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcDeptWorkRela(List<Map<String, Object>> entityList)throws DataAccessException;
	
	public String updateHtcDeptWorkRela(Map<String,Object> entityMap)throws DataAccessException;
}
