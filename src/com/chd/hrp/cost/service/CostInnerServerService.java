/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostInnerServer;

/**
* @Title. @Description.
* 内部服务量表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostInnerServerService {

	/**
	 * @Description 
	 * 内部服务量表<BR> 添加CostInnerServer
	 * @param CostInnerServer entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostInnerServer(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 批量添加CostInnerServer
	 * @param  CostInnerServer entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostInnerServer(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 查询CostInnerServer分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostInnerServer(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 查询CostInnerServerByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostInnerServer queryCostInnerServerByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 删除CostInnerServer
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostInnerServer(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 按月删除CostInnerServer
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteMonthlyCostInnerServer(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 内部服务量表<BR> 批量删除CostInnerServer
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostInnerServer(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 更新CostInnerServer
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostInnerServer(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 批量更新CostInnerServer
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostInnerServer(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 服务项目表<BR> 查询queryCostServerItemDict
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostInnerServer queryCostServerItemDict(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String,Object>> queryCostInnerServerPrint(Map<String,Object> entityMap) throws DataAccessException;
}
