package com.chd.hrp.htc.dao.task.deptcost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.deptcost.HtcDeptIassetDetail;
/**
 * 2015-3-24 author:alfred
 */

public interface HtcDeptIassetDetailMapper extends SqlMapper{
	
	public int addHtcDeptIassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptIassetDetail> queryHtcDeptIassetDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptIassetDetail> queryHtcDeptIassetDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcDeptIassetDetail queryHtcDeptIassetDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcDeptIassetDetail(List<Map<String,Object>> entityList)throws DataAccessException;
    
	public int updateHtcDeptIassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptIassetDetail> queryHtcDeptIassetCostDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcDeptIassetDetail> queryHtcDeptIassetCostDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HtcDeptIassetDetail> checkHtcDeptIassetCostDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptIassetDetail> checkHtcDeptIassetCostDetail(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
