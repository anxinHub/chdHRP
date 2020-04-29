package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.basic.HtcWageCostRela;
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

public interface HtcWageCostRelaMapper extends SqlMapper{
	
	public int addHtcWageCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcWageCostRela(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public List<HtcWageCostRela> queryHtcWageCostRela(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcWageCostRela> queryHtcWageCostRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcWageCostRela queryHtcWageCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcWageCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcWageCostRela(List<Map<String, Object>> entityList )throws DataAccessException;
    
	public int updateHtcWageCostRela(Map<String,Object> entityMap)throws DataAccessException;
}
