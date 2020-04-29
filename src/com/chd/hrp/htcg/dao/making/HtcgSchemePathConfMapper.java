package com.chd.hrp.htcg.dao.making;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemePathConf;
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

public interface HtcgSchemePathConfMapper extends SqlMapper{
	 
	public int addHtcgSchemePathConf(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgSchemePathConf(List<Map<String,Object>> list)throws DataAccessException;
	
	public int initHtcgSchemePathConf(Map<String,Object> entityMap)throws DataAccessException;
	 
	public List<HtcgSchemePathConf> queryHtcgSchemePathConf(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemePathConf> queryHtcgSchemePathConf(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	public HtcgSchemePathConf queryHtcgSchemePathConfByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgSchemePathConf(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgSchemePathConf(List<Map<String,Object>> list)throws DataAccessException;
	 
	public int updateHtcgSchemePathConf(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBatchHtcgSchemePathConf(List<Map<String,Object>> list)throws DataAccessException;
	
	
}
