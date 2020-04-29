package com.chd.hrp.med.service.account.report.outCategoryCount;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:
 * 出库分类统计:药品分类统计
 * @Table:无针对表
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedAccountReportMedTypeCountService {
	
	/**
	 * @Description 
	 * 药品分类统计 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedTypeCount(Map<String,Object> entityMap) throws DataAccessException;
}
