package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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

public interface HtcFassetDictMapper extends SqlMapper{
	
	public int addHtcFassetDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcFassetDict(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public List<HtcFassetDict> queryHtcFassetDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcFassetDict> queryHtcFassetDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcFassetDict queryHtcFassetDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcFassetDict(Map<String,Object> entityMap)throws DataAccessException;

    public int deleteBatchHtcFassetDict(List<Map<String, Object>> entityList)throws DataAccessException;
    
	public int updateHtcFassetDict(Map<String,Object> entityMap)throws DataAccessException;
	
}
