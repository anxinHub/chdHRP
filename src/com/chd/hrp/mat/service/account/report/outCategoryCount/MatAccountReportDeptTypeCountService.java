package com.chd.hrp.mat.service.account.report.outCategoryCount;

import java.util.List;
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
public interface MatAccountReportDeptTypeCountService {
	
	/**
	 * @Description 
	 * 科室类型统计 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatAccountReportDeptTypeCount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室类型统计 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatAccountReportDeptTypeCountPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室类型统计 查询动态列表头<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryDeptTypeCountHead(Map<String,Object> entityMap) throws DataAccessException;

	public String queryDeptTypeCountHeadNew(Map<String,Object> entityMap) throws DataAccessException;

	String queryOccurDeptTypeHead(Map<String, Object> entityMap)
			throws DataAccessException;
}
