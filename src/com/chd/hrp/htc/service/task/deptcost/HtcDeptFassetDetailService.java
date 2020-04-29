package com.chd.hrp.htc.service.task.deptcost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.deptcost.HtcDeptFassetDetail;
/** 
* 2015-3-18 
* author:alfred
*/ 
public interface HtcDeptFassetDetailService {
	
	public String addHtcDeptFassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcDeptFassetDetail(List<Map<String,Object>> list)throws DataAccessException;
	
	public String updateHtcDeptFassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptFassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public HtcDeptFassetDetail queryHtcDeptFassetDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptFassetCostDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String checkHtcDeptFassetCostDetail(Map<String,Object> entityMap)throws DataAccessException;
	
}
