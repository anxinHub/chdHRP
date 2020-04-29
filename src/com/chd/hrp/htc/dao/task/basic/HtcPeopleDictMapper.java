package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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

public interface HtcPeopleDictMapper extends SqlMapper{
	
	public int addHtcPeopleDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcPeopleDict(List<Map<String, Object>> entityList)throws DataAccessException;
	
	public List<HtcPeopleDict> queryHtcPeopleDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcPeopleDict> queryHtcPeopleDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcPeopleDict queryHtcPeopleDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcPeopleDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcPeopleDict(List<Map<String, Object>> entityList)throws DataAccessException;
    
	public int updateHtcPeopleDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int synchroHtcPeopleDict(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
