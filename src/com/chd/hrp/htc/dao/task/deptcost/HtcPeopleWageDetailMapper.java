package com.chd.hrp.htc.dao.task.deptcost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.deptcost.HtcPeopleWageDetail;
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

public interface HtcPeopleWageDetailMapper extends SqlMapper{
	
	public int addHtcPeopleWageDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcPeopleWageDetail(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryHtcPeopleWageItemClumHead(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryHtcPeopleWageDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryHtcPeopleWageDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcPeopleWageDetail queryHtcPeopleWageDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcPeopleWageDetail(Map<String,Object> entityMap)throws DataAccessException;
	
    public int deleteBatchHtcPeopleWageDetail(List<Map<String, Object>> entityList)throws DataAccessException;
    
	public int updateHtcPeopleWageDetail(Map<String,Object> entityMap)throws DataAccessException;
	
}
