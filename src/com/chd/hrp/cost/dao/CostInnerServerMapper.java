/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.cost.entity.CostInnerServer;

/**
* @Title. @Description.
* 内部服务量表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostInnerServerMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 添加CostInnerServer
	 * @param CostInnerServer entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostInnerServer(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 批量添加CostInnerServer
	 * @param  CostInnerServer entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostInnerServer(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 查询CostInnerServer分页
	 * @param  entityMap RowBounds
	 * @return List<CostInnerServer>
	 * @throws DataAccessException
	*/
	public List<CostInnerServer> queryCostInnerServer(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 内部服务量表<BR> 查询CostInnerServer所有数据
	 * @param  entityMap
	 * @return List<CostInnerServer>
	 * @throws DataAccessException
	*/
	public List<CostInnerServer> queryCostInnerServer(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 查询CostInnerServerByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostInnerServer queryCostInnerServerByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 删除CostInnerServer
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostInnerServer(Map<String,Object> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 内部服务量表<BR> 按鈕删除CostInnerServer
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int  deleteMonthlyCostInnerServer(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 内部服务量表<BR> 批量删除CostInnerServer
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostInnerServer(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 内部服务量表<BR> 更新CostInnerServer
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostInnerServer(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 内部服务量表<BR> 批量更新CostInnerServer
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostInnerServer(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 服务项目表<BR> 查询queryCostServerItemDict
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostInnerServer queryCostServerItemDict(Map<String,Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostInnerServerPrint(
			Map<String, Object> entityMap)throws DataAccessException;
}
