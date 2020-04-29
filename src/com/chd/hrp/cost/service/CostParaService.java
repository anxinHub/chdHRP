package com.chd.hrp.cost.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostPara;

public interface CostParaService {
	
	/**
	 * @Description 
	 * 系统参数<BR> 查询CostParaByCode
	 * @param  entityMap 
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryCostParaValue(Map<String,Object> entityMap,String para_code)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 系统参数<BR> 添加CostPara
	 * @param CostPara entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 批量添加CostPara
	 * @param  CostPara entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 查询CostPara分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostPara(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 查询CostParaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostPara queryCostParaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 删除CostPara
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 批量删除CostPara
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 更新CostPara
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 批量更新CostPara
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 系统参数<BR> 导入CostPara
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importCostPara(Map<String,Object> entityMap)throws DataAccessException;
	
}
