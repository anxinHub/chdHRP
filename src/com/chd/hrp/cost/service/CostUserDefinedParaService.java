/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostUserDefinedPara;

/**
* @Title. @Description.
* 自定义参数数据采集表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostUserDefinedParaService {

	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 添加CostUserDefinedPara
	 * @param CostUserDefinedPara entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostUserDefinedPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 批量添加CostUserDefinedPara
	 * @param  CostUserDefinedPara entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostUserDefinedPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 查询CostUserDefinedPara分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostUserDefinedPara(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 查询CostUserDefinedParaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostUserDefinedPara queryCostUserDefinedParaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 删除CostUserDefinedPara
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostUserDefinedPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 按月删除CostUserDefinedPara
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteMonthlyCostUserDefinedPara(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 批量删除CostUserDefinedPara
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostUserDefinedPara(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 更新CostUserDefinedPara
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostUserDefinedPara(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 自定义参数数据采集表<BR> 批量更新CostUserDefinedPara
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostUserDefinedPara(List<Map<String, Object>> entityMap)throws DataAccessException;
    /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	public String readAssFinaDictFiles(Map<String, Object> mapVo)throws DataAccessException;
	public List<Map<String,Object>> queryCostUserDefinedParaPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
     * 自定义参数数据采集
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	public String costUserDefinedParaExtendInheritance(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
     * 同步系统其它数据信息
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	public String synchroCostUserDefinedPara(Map<String,Object> entityMap)throws DataAccessException;
	
}
