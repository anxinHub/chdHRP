package com.chd.hrp.htcg.dao.making;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemeRecipeGroupRule;
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

public interface HtcgSchemeRecipeGroupRuleMapper extends SqlMapper{
	
	public int addHtcgSchemeRecipeGroupRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgSchemeRecipeGroupRule(List<Map<String,Object>> list)throws DataAccessException;
	
	public int initHtcgSchemeRecipeGroupRule(Map<String,Object> entityMap)throws DataAccessException;

	public List<HtcgSchemeRecipeGroupRule> queryHtcgSchemeRecipeGroupRule(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeRecipeGroupRule> queryHtcgSchemeRecipeGroupRule(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public HtcgSchemeRecipeGroupRule queryHtcgSchemeRecipeGroupRuleByCode(Map<String,Object> entityMap)throws DataAccessException;

	public int deleteHtcgSchemeRecipeGroupRule(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcgSchemeRecipeGroupRule(List<Map<String, Object>> list)throws DataAccessException;
    
    public int updateHtcgSchemeRecipeGroupRule(Map<String,Object> entityMap)throws DataAccessException;
	
    public int updateBatchHtcgSchemeRecipeGroupRule(List<Map<String, Object>> list)throws DataAccessException;
}
