/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.elseincome;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 其他收入预算
 * @Description:
 * 
 * @Table:
 * BUDG_ELSE_INCOME
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */

public interface BudgElseIncomeMapper extends SqlMapper{
	
	/**
	 * 判断收入预算科目是否存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjCodeExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 收入预算科目下拉框（添加时用）
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgIncomeSubj(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExist(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 根据填写数据 查询上年收入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String setLastIncome(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 生成 （根据上年执行数据生成）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addBudgElseIncome(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询当前年月的预算值、执行值、上月结转
	 * @param entityMap
	 * @return List<Map<String,Object>>
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgElseIncomeByYearMonth (Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量修改其它收入数据的上月结转、结转下月
	 * @param entityList
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateBatchBudgElseIncomeValue(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 查询其它收入预算科目
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryBudgElseIncomeSubj(Map<String, Object> entityList) throws DataAccessException;
}
