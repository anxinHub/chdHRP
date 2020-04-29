package com.chd.hrp.htcg.service.audit;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.audit.HtcgSchemeConf;
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

public interface HtcgSchemeConfService {

	public String initHtcgSchemeConf(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgSchemeConf(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchHtcgSchemeConf(List<Map<String, Object>> list)throws DataAccessException;
	
	
}
