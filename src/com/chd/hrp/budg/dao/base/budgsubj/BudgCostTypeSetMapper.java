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
import com.chd.hrp.budg.entity.BudgCostTypeSet;
import com.chd.hrp.budg.entity.BudgSelect;

/**
* @Title. @Description.
* 支出预算科目类别维护<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface BudgCostTypeSetMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 添加
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBudgCostTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 批量添加
	 * @param   entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchBudgCostTypeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 查询  分页
	 * @param  entityMap RowBounds
	 * @return List<BudgCostTypeSet>
	 * @throws DataAccessException
	*/
	public List<BudgCostTypeSet> queryBudgCostTypeSet(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 查询
	 * @param  entityMap
	 * @return List<BudgCostTypeSet>
	 * @throws DataAccessException
	*/
	public List<BudgCostTypeSet> queryBudgCostTypeSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 查询
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public BudgCostTypeSet queryBudgCostTypeSetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 删除
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBudgCostTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 批量删除
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchBudgCostTypeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 更新
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgCostTypeSet(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出预算科目类别维护<BR> 批量更新
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchBudgCostTypeSet(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 *修改
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updateBudgCostTypeSetById(Map<String, Object> entityMap) throws DataAccessException;
	
	//支出预算科目下拉框
	public List<BudgSelect> queryBudgCostSubj(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	//支出预算科目类别下拉框
	public List<BudgSelect> queryBudgCostSubjType(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	// 查询 支出预算科目类别数据是否已经存在（存在不允许添加）
	public int queryIsExist(Map<String, Object> mapVo) throws DataAccessException;
}
