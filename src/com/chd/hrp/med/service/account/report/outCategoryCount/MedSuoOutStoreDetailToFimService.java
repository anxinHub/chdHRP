package com.chd.hrp.med.service.account.report.outCategoryCount;

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
public interface MedSuoOutStoreDetailToFimService {
	
	/**
	 * @Description 
	 * 科室统计 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedSuoOutStoreDetailToFim(Map<String,Object> entityMap) throws DataAccessException;
 
}
