package com.chd.hrp.htcg.dao.making;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemeMrRule;
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

public interface HtcgSchemeMrRuleMapper extends SqlMapper{
	

	public int addHtcgSchemeMrRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public int initHtcgSchemeMrRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcgSchemeMrRule> queryHtcgSchemeMrRule(Map<String,Object> entityMap ) throws DataAccessException;
	public List<HtcgSchemeMrRule> queryHtcgSchemeMrRule(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcgSchemeMrRule queryHtcgSchemeMrRuleByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgSchemeMrRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgSchemeMrRule(List<Map<String, Object>> list)throws DataAccessException;

	public int updateHtcgSchemeMrRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBatchHtcgSchemeMrRule(List<Map<String, Object>> list)throws DataAccessException;
}
