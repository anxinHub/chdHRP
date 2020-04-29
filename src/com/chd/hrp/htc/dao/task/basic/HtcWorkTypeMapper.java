package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.basic.HtcWorkType;
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

public interface HtcWorkTypeMapper extends SqlMapper{
	
	/**
	 * 
	 */
	public int addHtcWorkType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int addBatchHtcWorkType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public List<HtcWorkType> queryHtcWorkType(Map<String,Object> entityMap) throws DataAccessException;
	public List<HtcWorkType> queryHtcWorkType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 */
	public HtcWorkType queryHtcWorkTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public int deleteHtcWorkType(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteBatchHtcWorkType(List<Map<String, Object>> entityList)throws DataAccessException;
    
	/**
	 * 
	 */
	public int updateHtcWorkType(Map<String,Object> entityMap)throws DataAccessException;
	
}
