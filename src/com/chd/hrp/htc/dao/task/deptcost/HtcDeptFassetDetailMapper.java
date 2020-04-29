package com.chd.hrp.htc.dao.task.deptcost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.deptcost.HtcDeptFassetDetail;
/** 
* 2015-3-18 
* author:alfred
*/ 

public interface HtcDeptFassetDetailMapper extends SqlMapper{
	
    public int addHtcDeptFassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcDeptFassetDetail(List<Map<String,Object>> list)throws DataAccessException;
	
	public int updateHtcDeptFassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptFassetDetail> queryHtcDeptFassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptFassetDetail> queryHtcDeptFassetDetail(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public HtcDeptFassetDetail queryHtcDeptFassetDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
    public List<HtcDeptFassetDetail> queryHtcDeptFassetCostDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptFassetDetail> queryHtcDeptFassetCostDetail(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
    public List<HtcDeptFassetDetail> checkHtcDeptFassetCostDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptFassetDetail> checkHtcDeptFassetCostDetail(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	
}
