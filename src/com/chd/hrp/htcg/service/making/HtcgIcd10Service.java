package com.chd.hrp.htcg.service.making;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.making.HtcgIcd10;
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

public interface HtcgIcd10Service {
 
	public String addHtcgIcd10(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgIcd10(List<Map<String,Object>> list)throws DataAccessException;
	 
	public String updateHtcgIcd10(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcgIcd10(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgIcd10(List<Map<String,Object>> list)throws DataAccessException;
	
	public String queryHtcgIcd10(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcgIcd10 queryHtcgIcd10ByCode(Map<String,Object> entityMap)throws DataAccessException;
	 
	public String importHtcgIcd10(Map<String, Object> entityMap) throws DataAccessException;

}
