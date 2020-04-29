package com.chd.hrp.htc.service.task.basic;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.basic.HtcResCauseDict;
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

public interface HtcResCauseDictService {

	public String addHtcResCauseDict(Map<String,Object> entityMap)throws DataAccessException;
	public String queryHtcResCauseDict(Map<String,Object> entityMap) throws DataAccessException;
	public HtcResCauseDict queryHtcResCauseDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	public String deleteHtcResCauseDict(Map<String,Object> entityMap)throws DataAccessException;
	public String deleteBatchHtcResCauseDict(List<Map<String, Object>> entityList)throws DataAccessException;
	public String updateHtcResCauseDict(Map<String,Object> entityMap)throws DataAccessException;
}
