/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.budg.service.base.budgsubj;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.budg.entity.BudgIncomeTypeSet;

/**
* @Title. @Description.
* 收入预算科目类别维护<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface BudgIncomeTypeSetService {

	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 添加
	 * @param BudgIncomeTypeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBudgIncomeTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 批量添加
	 * @param  BudgIncomeTypeSet entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchBudgIncomeTypeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 查询 分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryBudgIncomeTypeSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 查询
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public BudgIncomeTypeSet queryBudgIncomeTypeSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBudgIncomeTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 批量删除
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchBudgIncomeTypeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBudgIncomeTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 批量更新
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchBudgIncomeTypeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 导入
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	
	public String importBudgIncomeTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 继承
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String extendBudgIncomeTypeSet(Map<String, Object> mapVo) throws DataAccessException;

	//收入预算科目下拉框
	String queryBudgIncomeSubj(Map<String, Object> mapVo) throws DataAccessException;
	//收入预算科目类别下拉框
	String queryBudgIncomeSubjType(Map<String, Object> mapVo) throws DataAccessException;
	//查询 收入预算科目类别数据是否已经存在
	public int queryIsExist(Map<String, Object> mapVo) throws DataAccessException;

	public String save(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
	
}
