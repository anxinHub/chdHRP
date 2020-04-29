package com.chd.hrp.htc.service.info.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.info.basic.HtcCostTypeDict;
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

public interface HtcCostTypeDictService {

	public String addHtcCostTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcCostTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcCostTypeDict queryHtcCostTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcCostTypeDict(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public String updateHtcCostTypeDict(Map<String,Object> entityMap)throws DataAccessException;
}
