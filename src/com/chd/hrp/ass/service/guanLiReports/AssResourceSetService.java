/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.guanLiReports; 
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException; 
/**
 * 
 * @Description:
 * 050801 资产资金来源报表查询
 * @Table:
 * ASS_RESOURCE_SPECIAL_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssResourceSetService {

	 
	/**
	 * @Description 
	* 查询结果集050802 资产资金来源匹配报表查询   <BR>带分页 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssResourceSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssResourceSetPrint(Map<String, Object> entityMap) throws DataAccessException;





}
