/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 收入归集<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

 
public interface CostInComeCollectionService {

	public String queryIncomeCollection(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryIncomeCollectionPrmHead(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryIncomeCollectionPrm(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostCollectionMainCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryCostCollectionDetailCheck(Map<String,Object> entityMap) throws DataAccessException;
	
	public String addIncomeCollection(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryCostInComeCollectionPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryCostInComeCollectionPrmPrint(Map<String,Object> entityMap) throws DataAccessException;
}
