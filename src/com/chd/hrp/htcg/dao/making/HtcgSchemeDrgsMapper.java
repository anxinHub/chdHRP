package com.chd.hrp.htcg.dao.making;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemeDrgs;
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

public interface HtcgSchemeDrgsMapper extends SqlMapper{
	 
	public int addHtcgSchemeDrgs(Map<String,Object> entityMap)throws DataAccessException;
	 
	public int addBatchHtcgSchemeDrgs(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int deleteHtcgSchemeDrgs(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgSchemeDrgs(List<Map<String,Object>> entityMap)throws DataAccessException;
	 
	public int updateHtcgSchemeDrgs(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcgSchemeDrgs> queryHtcgSchemeDrgs(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeDrgs> queryHtcgSchemeDrgs(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	public HtcgSchemeDrgs queryHtcgSchemeDrgsByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	 
}
