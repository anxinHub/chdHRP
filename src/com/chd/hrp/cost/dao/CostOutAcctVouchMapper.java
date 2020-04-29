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
import com.chd.hrp.cost.entity.CostOutAcctVouch;

/**
* @Title. @Description.
* 科室支出总账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CostOutAcctVouchMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 添加CostOutAcctVouch
	 * @param CostOutAcctVouch entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostOutAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 批量添加CostOutAcctVouch
	 * @param  CostOutAcctVouch entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCostOutAcctVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 查询CostOutAcctVouch分页
	 * @param  entityMap RowBounds
	 * @return List<CostOutAcctVouch>
	 * @throws DataAccessException
	*/
	public List<CostOutAcctVouch> queryCostOutAcctVouch(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 科室支出总账<BR> 查询CostOutAcctVouch所有数据
	 * @param  entityMap
	 * @return List<CostOutAcctVouch>
	 * @throws DataAccessException
	*/
	public List<CostOutAcctVouch> queryCostOutAcctVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 查询CostOutAcctVouchByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostOutAcctVouch queryCostOutAcctVouchByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 删除CostOutAcctVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCostOutAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 批量删除CostOutAcctVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCostOutAcctVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 按照月份删除数据
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteMonthlyCostOutAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
    
	/**
	 * @Description 
	 * 科室支出总账<BR> 更新CostOutAcctVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCostOutAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室支出总账<BR> 批量更新CostOutAcctVouch
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostOutAcctVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询系统平台科室变更表<BR> 查询queryCostDeptByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public CostOutAcctVouch queryCostDeptByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 科室支出总账<BR> 采集数据
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCostOutAcctVouchByAcc(Map<String, Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 绩效成本支出<BR> 查询CostIncomeDetail所有数据
	 * @param  entityMap
	 * @return List<CostIncomeDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryCostOutAcctVouchPrm(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 绩效成本支出<BR> 查询CostIncomeDetail所有数据
	 * @param  entityMap
	 * @return List<CostIncomeDetail>
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryCostOutAcctVouchPrm(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryCostOutAcctVouchPrint(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryCostOutAcctVouchPrmPrint(Map<String, Object> entityMap)throws DataAccessException;
	
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
	public List<Map<String, Object>>  checkCostOutAcctVouch(Map<String,Object> entityMap)throws DataAccessException;
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
	public List<Map<String, Object>> checkCostOutAcctVouch(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
