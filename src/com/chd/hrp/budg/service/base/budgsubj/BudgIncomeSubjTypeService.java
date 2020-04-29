/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.budg.service.base.budgsubj;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.budg.entity.BudgIncomeSubjType;

/**
* @Title. @Description.
* 科目性质<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface BudgIncomeSubjTypeService {

	/**
	 * @Description 
	 * 收入科目类别<BR> 添加
	 * @param BudgIncomeSubjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBudgIncomeSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 批量添加
	 * @param  BudgIncomeSubjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchBudgIncomeSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 查询 分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryBudgIncomeSubjType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 查询
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public BudgIncomeSubjType queryBudgIncomeSubjTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBudgIncomeSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 批量删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchBudgIncomeSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBudgIncomeSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 批量更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchBudgIncomeSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 导入
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importBudgIncomeSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
}
