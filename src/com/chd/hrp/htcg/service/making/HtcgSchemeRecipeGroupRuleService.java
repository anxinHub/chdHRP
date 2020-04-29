package com.chd.hrp.htcg.service.making;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
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

public interface HtcgSchemeRecipeGroupRuleService {

	public String initHtcgSchemeRecipeGroupRule(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgSchemeRecipeGroupRule(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchHtcgSchemeRecipeGroupRule(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public String updateBatchHtcgSchemeRecipeGroupRule(List<Map<String,Object>> entityList)throws DataAccessException;
}
