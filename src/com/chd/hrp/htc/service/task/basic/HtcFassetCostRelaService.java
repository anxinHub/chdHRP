package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcFassetCostRela;
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

public interface HtcFassetCostRelaService {

	public String addHtcFassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcFassetCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcFassetCostRela queryHtcFassetCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcFassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcFassetCostRela(List<Map<String, Object>> entityList)throws DataAccessException;
	
	public String updateHtcFassetCostRela(Map<String,Object> entityMap)throws DataAccessException;
}
