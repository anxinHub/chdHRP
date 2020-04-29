package com.chd.hrp.htc.dao.task.deptcost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.deptcost.HtcPeopleBonusDetail;
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

public interface HtcPeopleBonusDetailMapper extends SqlMapper{
	
	public int addHtcPeopleBonusDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcPeopleBonusDetail(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryHtcPeopleBonusItemClumHead(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryHtcPeopleBonusDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryHtcPeopleBonusDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcPeopleBonusDetail queryHtcPeopleBonusDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcPeopleBonusDetail(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcPeopleBonusDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	public int updateHtcPeopleBonusDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
