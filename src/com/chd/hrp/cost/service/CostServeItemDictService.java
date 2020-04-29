/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostServeItemDict;


/**
* @Title. @Description.
*  内部服务项目<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostServeItemDictService {


	public String addCostServeItemDict(Map<String,Object> entityMap)throws DataAccessException;

	public String queryCostServeItemDict(Map<String,Object> entityMap) throws DataAccessException;

	public CostServeItemDict queryCostServeItemDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchCostAssDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public String updateCostAssDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	//打印
	public List<Map<String,Object>> queryCostServeItemDictPrint(Map<String,Object> entityMap) throws DataAccessException;
}
