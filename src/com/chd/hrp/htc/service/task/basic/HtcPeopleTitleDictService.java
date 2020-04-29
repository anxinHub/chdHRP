package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcPeopleTitleDict;
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

public interface HtcPeopleTitleDictService {

	public String addHtcPeopleTitleDict(Map<String,Object> entityMap)throws DataAccessException;

	public String queryHtcPeopleTitleDict(Map<String,Object> entityMap) throws DataAccessException;

	public HtcPeopleTitleDict queryHtcPeopleTitleDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteHtcPeopleTitleDict(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteBatchHtcPeopleTitleDict(List<Map<String,Object>> entityList) throws DataAccessException;
	
	public String updateHtcPeopleTitleDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String synchroHtcPeopleTitleDict(Map<String, Object> entityMap) throws DataAccessException;
	
	public String impHtcPeopleTitleDict(Map<String, Object> entityMap) throws DataAccessException;
}
