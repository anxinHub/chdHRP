package com.chd.hrp.htcg.service.making;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.making.HtcgScheme;
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

public interface HtcgSchemeService {
 
	public String addHtcgScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgScheme(List<Map<String,Object>> list)throws DataAccessException;
	
	public String queryHtcgScheme(Map<String,Object> entityMap) throws DataAccessException;
	 
	public HtcgScheme queryHtcgSchemeByCode(Map<String,Object> entityMap)throws DataAccessException;
	  
	public String deleteHtcgScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgScheme(List<Map<String,Object>> list)throws DataAccessException;
	 
	public String updateHtcgScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
