package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcPeopleDict;
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

public interface HtcPeopleDictService {

	public String addHtcPeopleDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcPeopleDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcPeopleDict queryHtcPeopleDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcPeopleDict(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteBatchHtcPeopleDict(List<Map<String,Object>> entityList) throws DataAccessException;

	public String updateHtcPeopleDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String synchroHtcPeopleDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String impHtcPeopleDict(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
