/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.storage.query;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Description:
 * 常州三院 个性化报表
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface MatDeptOutReportService{
	
	/**
	 * 大部门领用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatDeptOutReport(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 大部门领用 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatDeptOutReportPrint(Map<String, Object> entityMap) throws DataAccessException;

	public String queryMatStoreOutFimType(Map<String, Object> mapVo);
	
	public List<Map<String,Object>> queryMatStoreOutFimTypePrint(Map<String, Object> mapVo);
	

	String queryMatFinance(Map<String, Object> mapVo);
	
	List<Map<String,Object>> queryMatFinancePrint(Map<String, Object> mapVo);
	

	  
}
