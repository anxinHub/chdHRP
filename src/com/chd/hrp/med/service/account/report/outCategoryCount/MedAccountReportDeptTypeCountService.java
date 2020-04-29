package com.chd.hrp.med.service.account.report.outCategoryCount;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:
 * 出库分类统计:科室类型统计-查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedAccountReportDeptTypeCountService {
	
	/**
	 * @Description 
	 * 科室类型统计 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedAccountReportDeptTypeCount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室类型统计 查询动态列表头<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryDeptTypeCountHead(Map<String,Object> entityMap) throws DataAccessException;

	public String queryDeptTypeCountHeadNew(Map<String,Object> entityMap) throws DataAccessException;
}
