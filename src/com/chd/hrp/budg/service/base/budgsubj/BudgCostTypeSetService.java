/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.budg.service.base.budgsubj;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.budg.entity.BudgCostTypeSet;

/**
* @Title. @Description.
* 支出预算科目类别维护<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface BudgCostTypeSetService {

	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 添加
	 * @param BudgCostTypeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBudgCostTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 批量添加
	 * @param  BudgCostTypeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchBudgCostTypeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 查询 分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryBudgCostTypeSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 查询
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public BudgCostTypeSet queryBudgCostTypeSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBudgCostTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 批量删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchBudgCostTypeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBudgCostTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 批量更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchBudgCostTypeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 导入
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	
	public String importBudgCostTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 继承
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String extendBudgCostTypeSet(Map<String, Object> mapVo) throws DataAccessException;

	//支出预算科目下拉框
	String queryBudgCostSubj(Map<String, Object> mapVo) throws DataAccessException;
	//支出预算科目类别下拉框
	String queryBudgCostSubjType(Map<String, Object> mapVo) throws DataAccessException;
	//查询 支出预算科目类别数据是否已经存在
	public int queryIsExist(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 保存数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
	
}
