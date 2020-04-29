package com.chd.hrp.htcg.service.making;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

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

public interface HtcgSchemeMrRuleService {

    public String addHtcgSchemeMrRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public String initHtcgSchemeMrRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgSchemeMrRule(Map<String,Object> entityMap ) throws DataAccessException;
	
	public HtcgSchemeMrRule queryHtcgSchemeMrRuleByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcgSchemeMrRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgSchemeMrRule(List<Map<String, Object>> list)throws DataAccessException;
	
	public String updateHtcgSchemeMrRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateBatchHtcgSchemeMrRule(List<Map<String, Object>> list)throws DataAccessException;
}
