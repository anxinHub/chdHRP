package com.chd.hrp.mat.service.account.report.outCategoryCount;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:
 * 出库分类统计:物资分类统计
 * @Table:无针对表
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAccountReportMatTypeCountService {
	
	/**
	 * @Description 
	 * 物资分类统计 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatTypeCount(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 物资分类统计 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatTypeCountPrint(Map<String,Object> entityMap) throws DataAccessException;
}
