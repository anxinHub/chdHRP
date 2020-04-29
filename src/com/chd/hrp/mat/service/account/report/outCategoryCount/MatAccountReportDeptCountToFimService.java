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
public interface MatAccountReportDeptCountToFimService {
	
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
 
}
