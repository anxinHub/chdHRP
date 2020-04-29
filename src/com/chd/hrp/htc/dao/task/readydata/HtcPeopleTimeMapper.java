package com.chd.hrp.htc.dao.task.readydata;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.readydata.HtcPeopleTime;
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

public interface HtcPeopleTimeMapper extends SqlMapper{
	
	public int addHtcPeopleTime(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcPeopleTime> queryHtcPeopleTime(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcPeopleTime> queryHtcPeopleTime(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcPeopleTime queryHtcPeopleTimeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcPeopleTime(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcPeopleTime(List<Map<String, Object>> list)throws DataAccessException;
    
	public int updateHtcPeopleTime(Map<String,Object> entityMap)throws DataAccessException;
}
