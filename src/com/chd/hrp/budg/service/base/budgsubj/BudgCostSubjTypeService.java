/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.budg.service.base.budgsubj;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.budg.entity.BudgCostSubjType;

/**
* @Title. @Description.
* 支出科目类别<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface BudgCostSubjTypeService {

	/**
	 * @Description 
	 * 支出科目类别<BR> 添加
	 * @param BudgCostSubjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBudgCostSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 批量添加
	 * @param  BudgCostSubjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchBudgCostSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 查询 分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryBudgCostSubjType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 查询
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public BudgCostSubjType queryBudgCostSubjTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBudgCostSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 批量删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchBudgCostSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBudgCostSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 批量更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchBudgCostSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出科目类别<BR> 导入
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importBudgCostSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
}
