package com.chd.hrp.htcg.dao.making;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemeRecipeMergeRule;
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

public interface HtcgSchemeRecipeMergeRuleMapper extends SqlMapper{
	
	
	public int addHtcgSchemeRecipeMergeRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addHtcgBatchSchemeRecipeMergeRule(List<Map<String,Object>> list)throws DataAccessException;
	
	public int initHtcgSchemeRecipeMergeRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcgSchemeRecipeMergeRule> queryHtcgSchemeRecipeMergeRule(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeRecipeMergeRule> queryHtcgSchemeRecipeMergeRule(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public HtcgSchemeRecipeMergeRule queryHtcgSchemeRecipeMergeRuleByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgSchemeRecipeMergeRule(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcgSchemeRecipeMergeRule(List<Map<String, Object>> list)throws DataAccessException;
    
	public int updateSchemeRecipeMergeRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBatchHtcgSchemeRecipeMergeRule(List<Map<String, Object>> list)throws DataAccessException;
}
