package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcFassetDict;
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

public interface HtcFassetDictService {

	public String addHtcFassetDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcFassetDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcFassetDict queryHtcFassetDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcFassetDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcFassetDict(List<Map<String, Object>> entityList)throws DataAccessException;
	
	public String updateHtcFassetDict(Map<String,Object> entityMap)throws DataAccessException;
}
