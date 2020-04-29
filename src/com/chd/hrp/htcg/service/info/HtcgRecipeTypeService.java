/*
 *
 */package com.chd.hrp.htcg.service.info;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.info.HtcgRecipeType;
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

public interface HtcgRecipeTypeService {
 
	public String addHtcgRecipeType(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgRecipeType(List<Map<String,Object>> list)throws DataAccessException;
	 
	public String queryHtcgRecipeType(Map<String,Object> entityMap) throws DataAccessException;
	 
	public HtcgRecipeType queryHtcgRecipeTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	 
	public String deleteHtcgRecipeType(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgRecipeType(List<Map<String,Object>> list)throws DataAccessException;
	
	public String updateHtcgRecipeType(Map<String,Object> entityMap)throws DataAccessException;
	 
	public String importHtcgRecipeType(Map<String, Object> entityMap) throws DataAccessException;
}
