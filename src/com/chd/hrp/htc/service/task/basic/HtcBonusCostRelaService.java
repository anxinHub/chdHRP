package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcBonusCostRela;
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

public interface HtcBonusCostRelaService {

	public String addHtcBonusCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcBonusCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcBonusCostRela queryHtcBonusCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcBonusCostRela(List<Map<String,Object>> listVo)throws DataAccessException;
	
	public String deleteHtcBonusCostRela(Map<String, Object> entityMap)throws DataAccessException;
	
	public String updateHtcBonusCostRela(Map<String,Object> entityMap)throws DataAccessException;
}
