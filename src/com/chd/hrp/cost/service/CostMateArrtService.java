/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostMateArrt;

/**
* @Title. @Description.
* 成本_材料信息字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostMateArrtService {

	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 添加CostMateArrt
	 * @param CostMateArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostMateArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 批量添加CostMateArrt
	 * @param  CostMateArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostMateArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 查询CostMateArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostMateArrt(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 查询CostMateArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostMateArrt queryCostMateArrtByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 删除CostMateArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostMateArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 批量删除CostMateArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostMateArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 更新CostMateArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostMateArrt(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 批量更新CostMateArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostMateArrt(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 2016/10/31 lxj
	 * 成本_材料信息字典<BR> 同步CostMateArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String syncCostMateArrt(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 2016/10/31 lxj
	 * 成本_材料信息字典<BR> 同步CostMateArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String syncCostMateArrtNew(Map<String, Object> mapVo);
	//打印
	public List<Map<String,Object>> queryCostMateArrtPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
