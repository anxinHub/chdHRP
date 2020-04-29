package com.chd.hrp.htc.service.info.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.info.basic.HtcCostItemDict;
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

public interface HtcCostItemDictService {

	public String addHtcCostItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcCostItemDict(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public String queryHtcCostItemDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcCostItemDict queryHtcCostItemDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<?> queryHtcCostItemDictByTree(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteHtcCostItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcCostItemDict(List<Map<String,Object>> entityList)throws DataAccessException;
		 
	public String updateHtcCostItemDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateBatchHtcCostItemDict(List<Map<String, Object>> entityMap)throws DataAccessException;

	public String updateHtcCostItemBatch(Map<String, Object> entityMap) throws DataAccessException;
}
