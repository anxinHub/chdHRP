package com.chd.hrp.htc.service.task.deptcost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htc.entity.task.deptcost.HtcPeopleWageDetail;

public interface HtcPeopleWageDetailService {

	public String addHtcPeopleWageDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryHtcPeopleWageItemClumHead(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryHtcPeopleWageDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcPeopleWageDetail queryHtcPeopleWageDetailByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcPeopleWageDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcPeopleWageDetail(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public String updateHtcPeopleWageDetailItem(Map<String,Object> entityMap)throws DataAccessException;
	
}
