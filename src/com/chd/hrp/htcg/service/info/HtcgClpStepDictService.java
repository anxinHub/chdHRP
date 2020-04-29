/*
 *
 */package com.chd.hrp.htcg.service.info;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.info.HtcgClpStepDict;
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

public interface HtcgClpStepDictService {

	
	public String addHtcgClpStepDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgClpStepDict(List<Map<String,Object>> list)throws DataAccessException;
	
	public String queryHtcgClpStepDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcgClpStepDict queryHtcgClpStepDictByCode(Map<String,Object> entityMap) throws DataAccessException;
	
    public String deleteHtcgClpStepDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgClpStepDict(List<Map<String,Object>> list)throws DataAccessException;
	
	public String updateHtcgClpStepDict(Map<String,Object> entityMap) throws DataAccessException;

	public String importHtcgClpStepDict(Map<String, Object> entityMap) throws DataAccessException;
}
