package com.chd.hrp.htc.dao.task.readydata;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.readydata.HtcResCauseData;
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

public interface HtcResCauseDataMapper extends SqlMapper{ 
	
	
	public List<HtcResCauseData> queryHtcResCauseDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcResCauseData> queryHtcResCauseDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HtcResCauseData> queryHtcResCauseData(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcResCauseData> queryHtcResCauseData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int insertBatchHtcResCauseData(List<Map<String,Object>> list)throws DataAccessException;
	
	public int deleteBatchHtcResCauseData(List<Map<String,Object>> list)throws DataAccessException;

}
