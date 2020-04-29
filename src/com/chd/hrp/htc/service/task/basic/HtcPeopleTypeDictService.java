package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcPeopleTypeDict;
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

public interface HtcPeopleTypeDictService {


	
	public String addHtcPeopleTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcPeopleTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcPeopleTypeDict queryHtcPeopleTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcPeopleTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcPeopleTypeDict(List<Map<String,Object>> list)throws DataAccessException;

	public String updateHtcPeopleTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String impHtcPeopleTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String synchroHtcPeopleTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
}
