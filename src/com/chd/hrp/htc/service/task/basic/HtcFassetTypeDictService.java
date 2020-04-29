package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcFassetTypeDict;
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

public interface HtcFassetTypeDictService {

	public String addHtcFassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcFassetTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcFassetTypeDict queryHtcFassetTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcFassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteBatchHtcFassetTypeDict(List<Map<String, Object>> entityList)throws DataAccessException;
	
	public String updateHtcFassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String synchroHtcFassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;
}
