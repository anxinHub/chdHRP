/*
 *
 */package com.chd.hrp.htcg.service.info;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.info.HtcgOutcomeDict;
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

public interface HtcgOutcomeDictService {
 
	public String addHtcgOutcomeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgOutcomeDict(List<Map<String,Object>> list)throws DataAccessException;
	
	public String queryHtcgOutcomeDict(Map<String,Object> entityMap) throws DataAccessException;
	 
	public HtcgOutcomeDict queryHtcgOutcomeDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcgOutcomeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgOutcomeDict(List<Map<String,Object>> list)throws DataAccessException;
	 
	public String updateHtcgOutcomeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String importHtcgOutcomeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	
}
