package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcWageCostRela;
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

public interface HtcWageCostRelaService {

	public String addHtcWageCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcWageCostRela(List<Map<String,Object>> entityMap)throws DataAccessException;

	public String queryHtcWageCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcWageCostRela queryHtcWageCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcWageCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcWageCostRela(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String updateHtcWageCostRela(Map<String,Object> entityMap)throws DataAccessException;
}
