/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.cost.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.cost.entity.CostOutAcctVouch;

/**
* @Title. @Description.
* 科室支出总账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostOutAcctVouchService {

	/**
	 * @Description 
	 * 科室支出总账<BR> 添加CostOutAcctVouch
	 * @param CostOutAcctVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostOutAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 批量添加CostOutAcctVouch
	 * @param  CostOutAcctVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCostOutAcctVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 查询CostOutAcctVouch分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostOutAcctVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 查询CostOutAcctVouchByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostOutAcctVouch queryCostOutAcctVouchByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 删除CostOutAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCostOutAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 批量删除CostOutAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCostOutAcctVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 更新CostOutAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCostOutAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 批量更新CostOutAcctVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCostOutAcctVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询系统平台科室变更表<BR> 查询queryCostDeptByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public CostOutAcctVouch queryCostDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 采集会计数据
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCostOutAcctVouchByAcc(Map<String, Object> entityMap)throws DataAccessException;
    /**
     * 导入 
     * @param mapVo
     * @return
     */
	public String readAssFinaDictFiles(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 导入 
	 * @param mapVo
	 * @return
	 */
	public String readCostItemDictFilesX(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 用于绩效<BR> 查询CostOutAcctVouch分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCostOutAcctVouchPrm(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * <BR> 按照月份删除数据
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteMonthlyCostOutAcctVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	* @Title: queryCostOutAcctVouch
	* @Description: 校验会计数据查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月13日   
	* @author sjy
	 */
	public String checkCostOutAcctVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	//打印
	public List<Map<String,Object>> queryCostOutAcctVouchPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryCostOutAcctVouchPrmPrint(Map<String,Object> entityMap) throws DataAccessException;
}
