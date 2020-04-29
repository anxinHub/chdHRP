package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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

public interface HtcPeopleTypeDictMapper extends SqlMapper{
	
	
	public int addHtcPeopleTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addbatchHtcPeopleTypeDict(List<Map<String,Object>> list)throws DataAccessException;

	public List<HtcPeopleTypeDict> queryHtcPeopleTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcPeopleTypeDict> queryHtcPeopleTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcPeopleTypeDict queryHtcPeopleTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateHtcPeopleTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcPeopleTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcPeopleTypeDict(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int synchroHtcPeopleTypeDict(Map<String,Object> entityMap)throws DataAccessException;
}
