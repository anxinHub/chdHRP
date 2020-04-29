
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.service.base.budgsubj;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.budg.entity.BudgIncomeSubj;
/**
 * 
 * @Description:
 * 收入性质
 * @Table:
 * INCOME_BUDG_SUBJ
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface BudgIncomeSubjService {

	/**
	 * @Description 
	 * 添加收入性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBudgIncomeSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加收入性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchBudgIncomeSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新收入性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBudgIncomeSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新收入性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchBudgIncomeSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 批量更新科目类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String budgBathUpdate(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除收入性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBudgIncomeSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除收入性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchBudgIncomeSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集收入性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryBudgIncomeSubj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象收入性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public BudgIncomeSubj queryBudgIncomeSubjByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 判断字典中数据是否被引用<BR>
	 *              引用则不允许删除
	 * @param tableName
	 *            表名
	 * @param id
	 *            表的主键
	 * @param p_flag
	 *            表是否带级次 0:不带 1：带  
	 * @return
	 * @throws DataAccessException
	 */
	public String isExistsDataByTable(String tableName, Object id) throws DataAccessException;
	
	public Map<String, Object> querySup(Map<String, Object> mapVo)throws DataAccessException;
	public Map<String, Object> queryAccTypeCodeByCode(Map<String, Object> mapVo)throws DataAccessException;
	
	
}
