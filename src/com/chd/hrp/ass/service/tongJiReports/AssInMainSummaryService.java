/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.tongJiReports; 
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException; 
/**
 * 
 * @Description:
 * 050801 入库金额 根据库房  供应商  汇总
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssInMainSummaryService {    

	 
	/**
	 * @Description 
	 * 查询结果集050802 资产入库汇总   <BR>带分页 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssInMainBySummary(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 资产入库分类汇总
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssInMainSummaryByType(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryInMainSituation(Map<String, Object> entityMap) throws DataAccessException;
	
	 /**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssInMainSummaryPrint(Map<String, Object> entityMap) throws DataAccessException;


	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssInMainSummaryByTypePrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryInMainSituationPrint(Map<String, Object> entityMap) throws DataAccessException;
}
