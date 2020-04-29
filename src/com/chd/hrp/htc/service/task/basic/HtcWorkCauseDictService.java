package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcWorkCauseDict;
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

public interface HtcWorkCauseDictService {

	/**
	 * 
	 */
	public String addHtcWorkCauseDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public String queryHtcWorkCauseDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public HtcWorkCauseDict queryHtcWorkCauseDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public String deleteHtcWorkCauseDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public String deleteBatchHtcWorkCauseDict(List<Map<String, Object>> entityList)throws DataAccessException;
	
	/**
	 * 
	 */
	public String updateHtcWorkCauseDict(Map<String,Object> entityMap)throws DataAccessException;
}
