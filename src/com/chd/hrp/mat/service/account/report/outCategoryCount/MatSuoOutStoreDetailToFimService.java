package com.chd.hrp.mat.service.account.report.outCategoryCount;

import java.text.ParseException;
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
public interface MatSuoOutStoreDetailToFimService {
	
	/**
	 * @Description 
	 * 科室统计 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 * @throws ParseException  
	*/
	public String queryMatSuoOutStoreDetailToFim(Map<String,Object> entityMap) throws DataAccessException, ParseException;
	/**
	 * @Description 
	 * 科室统计 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 * @throws ParseException 
	 */
	public List<Map<String,Object>> queryMatSuoOutStoreDetailToFimPrint(Map<String,Object> entityMap) throws DataAccessException, ParseException;
	/**
	 * 查询发生了出库业务的物资材料编码,名称
	 * @param mapVo
	 * @return
	 * @throws ParseException 
	 */
	public String queryOccurOutMatFimTypeDictForHead(Map<String, Object> mapVo) throws ParseException;
 
}
