package com.chd.hrp.htc.service.task.deptcost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.deptcost.HtcDeptMaterialDetail;
/** 
* 2015-3-18 
* author:alfred
*/ 
public interface HtcDeptMaterialDetailService {

	public String addHtcDeptMaterialDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptMaterialDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcDeptMaterialDetail queryHtcDeptMaterialDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcDeptMaterialDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcDeptMaterialDetail(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public String updateHtcDeptMaterialDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptMaterialCostDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public String checkHtcDeptMaterialCostDetail(Map<String,Object> entityMap)throws DataAccessException;
}
