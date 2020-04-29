package com.chd.hrp.htcg.dao.making;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.HtcgClpStepRule;
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

public interface HtcgClpStepRuleMapper extends SqlMapper{
	
	
	public int addHtcgClpStepRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgClpStepRule(List<Map<String,Object>> list)throws DataAccessException;
	
	public int initHtcgClpStepRule(Map<String,Object> entityMap)throws DataAccessException;

	public List<HtcgClpStepRule> queryHtcgClpStepRule(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgClpStepRule> queryHtcgClpStepRule(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcgClpStepRule queryHtcgClpStepRuleByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgClpStepRule(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcgClpStepRule(List<Map<String,Object>> list)throws DataAccessException;
    
	public int updateHtcgClpStepRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBatchHtcgClpStepRule(List<Map<String,Object>> list)throws DataAccessException;
}
