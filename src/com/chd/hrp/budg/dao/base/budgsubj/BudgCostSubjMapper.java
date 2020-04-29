
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.budg.dao.base.budgsubj;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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
 


public interface BudgCostSubjMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加支出性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBudgCostSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加支出性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchBudgCostSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新支出性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgCostSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新支出性质<BR> 
	 * @param  entityMap
	 * @return BudgCostSubj
	 * @throws DataAccessException
	*/
	public int updateBatchBudgCostSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除支出性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBudgCostSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除支出性质<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchBudgCostSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集支出性质<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<BudgCostSubj> queryBudgCostSubj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集支出性质<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<BudgCostSubj> queryBudgCostSubj(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取支出性质<BR> 
	 * @param  entityMap
	 * @return BudgCostSubj
	 * @throws DataAccessException
	*/
	public BudgCostSubj queryBudgCostSubjByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据 输入的 科目编码 查询其上级科目编码、科目全称（上级科目编码不存在则不允许添加、修改）
	 * @param mapVo
	 * @return
	 */
	public BudgCostSubj qureySurp_code(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改 年度、科目编码时 修改支出预算科目
	 * @param entityMap
	 * @return
	 */
	public int updateBudgCostSubjByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询科目名称是否已占用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int qureyNameExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询所选年度数据是否已有数据  继承功能使用
	 * @param entityMap
	 * @return
	 */
	public int queryDataExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询上年度数据
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryLastYearData(Map<String, Object> entityMap) throws DataAccessException;
	public Map<String,Object> prcUpdateBudgCostSubjALL(Map<String,Object> entityMap) throws  DataAccessException;
	/**
	 * @Description 
	 * 批量更新预算支出科目 科目类别<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCostType(List<Map<String, Object>> listVo)throws  DataAccessException;
	
	public Map<String, Object> querySup(Map<String, Object> entityMap)throws  DataAccessException;
	
	public void updateBatchBudgCostSubjLast(List<Map<String, Object>> entityList)throws  DataAccessException;
	
}
