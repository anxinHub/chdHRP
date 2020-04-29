
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
import com.chd.hrp.budg.entity.BudgAccSubjShip;
import com.chd.hrp.budg.entity.BudgIncomeSubj;

/**
 * 
 * @Description:
 * 收入预算科目
 * @Table:
 * INCOME_BUDG_SUBJ
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface BudgIncomeSubjMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加收入预算科目<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBudgIncomeSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加收入预算科目<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchBudgIncomeSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新收入预算科目<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBudgIncomeSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新收入预算科目<BR> 
	 * @param  entityMap
	 * @return BudgIncomeSubj
	 * @throws DataAccessException
	*/
	public int updateBatchBudgIncomeSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除收入预算科目<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBudgIncomeSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除收入预算科目<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchBudgIncomeSubj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集收入预算科目<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<BudgIncomeSubj> queryBudgIncomeSubj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集收入预算科目<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<BudgIncomeSubj> queryBudgIncomeSubj(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取收入预算科目<BR> 
	 * @param  entityMap
	 * @return IncomeBudgSubj
	 * @throws DataAccessException
	*/
	public BudgIncomeSubj queryBudgIncomeSubjByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 根据 输入的 科目编码 查询其上级科目编码、科目全称（上级科目编码不存在则不允许添加、修改）
	 * @param mapVo
	 * @return
	 */
	public BudgIncomeSubj qureySurp_code(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改收入预算科目编码时 修改预算科目记录
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgIncomeSubjByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询科目名称是否被占用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> entityMap) throws DataAccessException;
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
	public Map<String,Object> prcUpdateBudgIncomeSubjALL(Map<String,Object> entityMap) throws  DataAccessException;

	public void updateIsLast(Map<String, Object> updateMap)throws  DataAccessException;

	public int queryBudgSubjKind(Map<String, Object> entityMap)throws  DataAccessException;

	public void updateBatchBudgIncomeSubjLast(List<Map<String, Object>> entityList)throws  DataAccessException;

	public int budgBathUpdate(List<Map<String, Object>> listVo)throws  DataAccessException;

	public Map<String, Object> querySup(Map<String, Object> entityMap)throws  DataAccessException;

	public Map<String, Object> queryAccTypeCodeByCode(Map<String, Object> entityMap)throws  DataAccessException;
	
}
