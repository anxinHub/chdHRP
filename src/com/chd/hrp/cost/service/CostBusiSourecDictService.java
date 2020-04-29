/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostBusiSourecDict;

/**
* @Title. @Description.
* 奖金明细数据与工资项关系表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostBusiSourecDictService {

	public String queryCostBusiSourecDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public String addCostBusiSourecDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public String deleteBatchCostBusiSourecDict(List<Map<String, Object>> list) throws DataAccessException;
	
	public CostBusiSourecDict queryCostBusiSourecDictByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public String updateCostBusiSourecDict(Map<String,Object> entityMap) throws DataAccessException;
}
