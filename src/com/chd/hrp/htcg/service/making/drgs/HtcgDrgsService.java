/*
 *
 */package com.chd.hrp.htcg.service.making.drgs;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.making.drgs.HtcgDrgs;
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

public interface HtcgDrgsService {
 
	public String addHtcgDrgs(Map<String,Object> entityMap)throws DataAccessException;
	  
	public String deleteBatchHtcgDrgs(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public String updateHtcgDrgs(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcgDrgs(Map<String,Object> entityMap) throws DataAccessException;
	 
	public HtcgDrgs queryHtcgDrgsByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String importHtcgDrgs(Map<String, Object> entityMap) throws DataAccessException;
	
	 
}
