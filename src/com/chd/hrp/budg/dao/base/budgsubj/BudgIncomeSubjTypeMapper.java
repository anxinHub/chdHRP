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
import com.chd.hrp.budg.entity.BudgIncomeSubjType;

/**
* @Title. @Description.
* 收入科目类别<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface BudgIncomeSubjTypeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 添加
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBudgIncomeSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 批量添加
	 * @param   entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchBudgIncomeSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 查询  分页
	 * @param  entityMap RowBounds
	 * @return List<BudgIncomeSubjType>
	 * @throws DataAccessException
	*/
	public List<BudgIncomeSubjType> queryBudgIncomeSubjType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 收入科目类别<BR> 查询
	 * @param  entityMap
	 * @return List<BudgIncomeSubjType>
	 * @throws DataAccessException
	*/
	public List<BudgIncomeSubjType> queryBudgIncomeSubjType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 查询
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public BudgIncomeSubjType queryBudgIncomeSubjTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 删除
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBudgIncomeSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 批量删除
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchBudgIncomeSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 收入科目类别<BR> 更新
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgIncomeSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 收入科目类别<BR> 批量更新
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchBudgIncomeSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 *修改收入科目类别编码时 修改科目性质记录
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updateBudgIncomeSubjTypeById(Map<String, Object> entityMap) throws DataAccessException;
}
