/*
 *
 */package com.chd.hrp.htcg.dao.making.drgs;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.drgs.HtcgDrgs;
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

public interface HtcgDrgsMapper extends SqlMapper{
	 
	public int addHtcgDrgs(Map<String,Object> entityMap)throws DataAccessException;
	 
	public int addBatchHtcgDrgs(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int deleteHtcgDrgs(Map<String,Object> entityMap)throws DataAccessException; 
	
	public int deleteBatchHtcgDrgs(List<Map<String,Object>> entityList)throws DataAccessException; 
	
	public int updateHtcgDrgs(Map<String,Object> entityMap)throws DataAccessException;
	 
	public List<HtcgDrgs> queryHtcgDrgs(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgDrgs> queryHtcgDrgs(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	public HtcgDrgs queryHtcgDrgsByCode(Map<String,Object> entityMap)throws DataAccessException;
	 
}
