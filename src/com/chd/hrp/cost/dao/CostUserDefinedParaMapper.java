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
import com.chd.hrp.cost.entity.CostUserDefinedPara;

/**
* @Title. @Description.
* 自定义参数数据采集表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostUserDefinedParaMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 添加CostUserDefinedPara
	 * @param CostUserDefinedPara entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostUserDefinedPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 添加CostUserDefinedPara
	 * @param CostUserDefinedPara entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostUserDefinedExtendPara(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 批量添加CostUserDefinedPara
	 * @param  CostUserDefinedPara entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostUserDefinedPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 查询CostUserDefinedPara分页
	 * @param  entityMap RowBounds
	 * @return List<CostUserDefinedPara>
	 * @throws DataAccessException
	*/
	public List<CostUserDefinedPara> queryCostUserDefinedPara(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 查询CostUserDefinedPara所有数据
	 * @param  entityMap
	 * @return List<CostUserDefinedPara>
	 * @throws DataAccessException
	*/
	public List<CostUserDefinedPara> queryCostUserDefinedPara(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 查询CostUserDefinedParaByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostUserDefinedPara queryCostUserDefinedParaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 删除CostUserDefinedPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostUserDefinedPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 按月删除deleteMonthlyCostUserDefinedPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	  public int deleteMonthlyCostUserDefinedPara(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 删除CostUserDefinedPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostUserDefinedExtendPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 批量删除CostUserDefinedPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostUserDefinedPara(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 更新CostUserDefinedPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostUserDefinedPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 批量更新CostUserDefinedPara
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostUserDefinedPara(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostUserDefinedParaPrint(
			Map<String, Object> entityMap)throws DataAccessException;
	

	public List<Map<String, Object>> queryIncomeMain(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryIncomeDetail(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> getCostClinicWork(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> getCostInhosWork(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
}
