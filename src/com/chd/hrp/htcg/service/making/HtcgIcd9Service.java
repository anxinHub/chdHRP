package com.chd.hrp.htcg.service.making;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.making.HtcgIcd9;
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

public interface HtcgIcd9Service {
 
	public String addHtcgIcd9(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgIcd9(Map<String,Object> entityMap) throws DataAccessException;
	 
	public HtcgIcd9 queryHtcgIcd9ByCode(Map<String,Object> entityMap)throws DataAccessException;
 
	public String deleteBatchHtcgIcd9(List<Map<String,Object>> entityList)throws DataAccessException;
	 
	public String updateHtcgIcd9(Map<String,Object> entityMap)throws DataAccessException;
	
	public String importHtcgIcd9(Map<String, Object> entityMap) throws DataAccessException;
	
	
	
	
}
