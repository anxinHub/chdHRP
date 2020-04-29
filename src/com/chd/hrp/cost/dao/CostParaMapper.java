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

import com.chd.hrp.cost.entity.CostPara;

/**
* @Title. @Description.
* 系统参数<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostParaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 系统参数<BR> 添加CostPara
	 * @param CostPara entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 批量添加CostPara
	 * @param  CostPara entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 查询CostPara分页
	 * @param  entityMap RowBounds
	 * @return List<CostPara>
	 * @throws DataAccessException
	*/
	public List<CostPara> queryCostPara(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 系统参数<BR> 查询CostPara所有数据
	 * @param  entityMap
	 * @return List<CostPara>
	 * @throws DataAccessException
	*/
	public List<CostPara> queryCostPara(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 查询CostParaByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostPara queryCostParaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 删除CostPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 批量删除CostPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostPara(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 系统参数<BR> 更新CostPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 批量更新CostPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostPara(List<Map<String, Object>> entityMap)throws DataAccessException;
}
