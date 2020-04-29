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

public interface HtcgSchemePathConfService {
 
	public String initHtcgSchemePathConf(Map<String,Object> entityMap)throws DataAccessException;
	 
	public String queryHtcgSchemePathConf(Map<String,Object> entityMap) throws DataAccessException;
	 
	public String deleteBatchHtcgSchemePathConf(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public String saveHtcgSchemePathConf(List<Map<String,Object>> list)throws DataAccessException;
	
}
