/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 科室成本归集<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
 

public interface CostCollectionService {

	

	public String queryCostCollection(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostCollectionPrmHead(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostCollectionPrm(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String addCostCollection(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryCostCollectionPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryCostCollectionPrmPrint(Map<String,Object> entityMap) throws DataAccessException;

	
}
