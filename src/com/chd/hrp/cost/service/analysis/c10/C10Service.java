/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.service.analysis.c10;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 指标分析服务类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface C10Service  {
	/**
	 * @Description 
	 * 医院成本绩效分析 1001<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public String queryAnalysisC1001(Map<String,Object> entityMap) throws DataAccessException;
	
	public String addAnalysisC1001(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryC1001Print(Map<String,Object> entityMap) throws DataAccessException;
	
}
