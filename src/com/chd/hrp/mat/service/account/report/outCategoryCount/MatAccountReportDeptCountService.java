package com.chd.hrp.mat.service.account.report.outCategoryCount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:
 * 出库分类统计:科室统计-查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAccountReportDeptCountService {
	
	/**
	 * @Description 
	 * 科室统计 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatAccountReportDeptCount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室统计 打印<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatAccountReportDeptCountPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室统计 查询动态列表头<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryDeptCountHead(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 移库汇总表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatTransferCount(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 移库汇总表 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatTransferCountprint(Map<String, Object> entityMap) throws DataAccessException;
}
