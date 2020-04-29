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
 * 050801 入库金额  供应商  汇总
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssInMainSummaryByVenService {

	 
	/**
	 * @Description 
	 * 查询结果集050802 资产入库汇总   <BR>带分页 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssInMainSummaryByVen(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssInMainSummaryByVenPrint(Map<String, Object> entityMap) throws DataAccessException;




}
