package com.chd.hrp.htc.service.task.projectcost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.projectcost.HtcDeptMateRela;
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

public interface HtcDeptMateRelaService {
	
	
	public String addHtcDeptMateRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptMateRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcDeptMateRela queryHtcDeptMateRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcDeptMateRela(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public String updateHtcDeptMateRela(Map<String,Object> entityMap)throws DataAccessException;
}
