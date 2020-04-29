/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.budg.dao.base.budgsubj;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgIncomeTypeSet;
import com.chd.hrp.budg.entity.BudgSelect;

/**
* @Title. @Description.
* 收入预算科目类别维护<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface BudgIncomeTypeSetMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 添加
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBudgIncomeTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 批量添加
	 * @param   entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchBudgIncomeTypeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 查询  分页
	 * @param  entityMap RowBounds
	 * @return List<BudgIncomeTypeSet>
	 * @throws DataAccessException
	*/
	public List<BudgIncomeTypeSet> queryBudgIncomeTypeSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 查询
	 * @param  entityMap
	 * @return List<BudgIncomeTypeSet>
	 * @throws DataAccessException
	*/
	public List<BudgIncomeTypeSet> queryBudgIncomeTypeSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 查询
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public BudgIncomeTypeSet queryBudgIncomeTypeSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 删除
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBudgIncomeTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 批量删除
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchBudgIncomeTypeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 更新
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgIncomeTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入预算科目类别维护<BR> 批量更新
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchBudgIncomeTypeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 *修改
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updateBudgIncomeTypeSetById(Map<String, Object> entityMap) throws DataAccessException;
	
	//收入预算科目下拉框
	public List<BudgSelect> queryBudgIncomeSubj(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	//收入预算科目类别下拉框
	public List<BudgSelect> queryBudgIncomeSubjType(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	// 查询 收入预算科目类别数据是否已经存在（存在不允许添加）
	public int queryIsExist(Map<String, Object> mapVo) throws DataAccessException;
}
