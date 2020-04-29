package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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

public interface HtcFassetTypeDictMapper extends SqlMapper{
	
	public int addHtcFassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcFassetTypeDict(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public List<HtcFassetTypeDict> queryHtcFassetTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcFassetTypeDict> queryHtcFassetTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public HtcFassetTypeDict queryHtcFassetTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	public int deleteHtcFassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;

    public int deleteBatchHtcFassetTypeDict(List<Map<String, Object>> entityList)throws DataAccessException;

	public int updateHtcFassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int synchroHtcFassetTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
