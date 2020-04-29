package com.chd.hrp.htcg.service.making.drgs;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.making.drgs.HtcgDrgsType;
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

public interface HtcgDrgsTypeService {
 
	public String addHtcgDrgsType(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgDrgsType(List<Map<String,Object>> list)throws DataAccessException;
	 
	public String queryHtcgDrgsType(Map<String,Object> entityMap) throws DataAccessException;
	 
	public HtcgDrgsType queryHtcgDrgsTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	 
	public String deleteHtcgDrgsType(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgDrgsType(List<Map<String,Object>> list)throws DataAccessException;
	 
	public String updateHtcgDrgsType(Map<String,Object> entityMap)throws DataAccessException;
	
	public String importHtcgDrgsType(Map<String, Object> entityMap) throws DataAccessException;
	
	
}
