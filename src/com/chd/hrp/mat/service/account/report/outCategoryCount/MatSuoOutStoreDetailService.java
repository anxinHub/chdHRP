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
public interface MatSuoOutStoreDetailService {
	
	/**
	 * @Description 
	 * 科室统计 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatSuoOutStoreDetail(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 科室统计 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatSuoOutStoreDetailPrint(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 获取发生过出库业务的物资类型编码,名称
	 * @param mapVo
	 * @return
	 */
	public String queryOccurOutMatTypeDictForHead(Map<String, Object> mapVo);
 
}
