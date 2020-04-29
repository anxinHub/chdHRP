package com.chd.hrp.htc.dao.task.deptcost;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.deptcost.HtcDeptMaterialDetail;
/** 
* 2015-3-18 
* author:alfred
*/ 

public interface HtcDeptMaterialDetailMapper extends SqlMapper{
	
	public int addHtcDeptMaterialDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcDeptMaterialDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<HtcDeptMaterialDetail> queryHtcDeptMaterialDetail(Map<String,Object> entityMap) throws DataAccessException;
	public List<HtcDeptMaterialDetail> queryHtcDeptMaterialDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcDeptMaterialDetail queryHtcDeptMaterialDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcDeptMaterialDetail(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteBatchHtcDeptMaterialDetail(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public int updateHtcDeptMaterialDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcDeptMaterialDetail> queryHtcDeptMaterialCostDetail(Map<String,Object> entityMap) throws DataAccessException;
	public List<HtcDeptMaterialDetail> queryHtcDeptMaterialCostDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<HtcDeptMaterialDetail> checkHtcDeptMaterialCostDetail(Map<String,Object> entityMap)throws DataAccessException;
	public List<HtcDeptMaterialDetail> checkHtcDeptMaterialCostDetail(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
}
