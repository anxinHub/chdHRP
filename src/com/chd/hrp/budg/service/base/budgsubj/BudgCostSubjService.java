
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.service.base.budgsubj;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.budg.entity.BudgCostSubj;
/**
 * 
 * @Description:
 * 支出性质
 * @Table:
 * COST_BUDG_SUBJ
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface BudgCostSubjService {

	/**
	 * @Description 
	 * 添加支出性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBudgCostSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加支出性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchBudgCostSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新支出性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBudgCostSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新支出性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchBudgCostSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除支出性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBudgCostSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除支出性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchBudgCostSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集支出性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryBudgCostSubj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象支出性质<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public BudgCostSubj queryBudgCostSubjByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 
	 * @param mapVo
	 * @return
	 */
	public String getSuperCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String extendBudgCostSubj(Map<String, Object> page)throws DataAccessException;
	/**
	 * @Description 
	 * 批量更新支出科目类别<BR> 
	 * @param  entityMap
	 * @return string
	 * @throws DataAccessException
	*/
	public String updateBatchCostType(List<Map<String, Object>> entityMap)throws DataAccessException;
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
	
}
