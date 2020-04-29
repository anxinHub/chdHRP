package com.chd.hrp.med.service.account.report;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description:
 * 科室出库查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedAccountReportDeptOutSearchService {
	
	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedAccountReportDeptOutSearch(Map<String,Object> entityMap) throws DataAccessException;
}
