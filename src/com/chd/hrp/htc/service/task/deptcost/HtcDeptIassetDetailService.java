package com.chd.hrp.htc.service.task.deptcost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.deptcost.HtcDeptIassetDetail;
/**
 * 2015-3-25 author:alfred
 */

public interface HtcDeptIassetDetailService {

	public String addHtcDeptIassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptIassetDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcDeptIassetDetail queryHtcDeptIassetDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcDeptIassetDetail(List<Map<String,Object>> entityList)throws DataAccessException;
	
	public String updateHtcDeptIassetDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcDeptIassetCostDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public String checkHtcDeptIassetCostDetail(Map<String,Object> entityMap)throws DataAccessException;
}
