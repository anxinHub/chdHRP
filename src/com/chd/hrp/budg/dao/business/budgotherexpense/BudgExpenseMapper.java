/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.budgotherexpense;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgExpense;
/**
 * 
 * @Description:
 * 科室其他费用预算
 * @Table:
 * BUDG_EXPENSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 
public interface BudgExpenseMapper extends SqlMapper{
	/**
	 * 根据主键查询数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDataByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询支出项目在三张表中是否重复出现编制
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryExistsPaymentItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询申请及其他费用数据
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryDataFromApplyAndElse(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询定额数据  匹配月份使用 
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryDataFromFix(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 批量添加生成数据 
	 * @param mapVo
	 * @return
	 */
	public int addGenerateBatch(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 查询 科室基本信息(根据code 匹配ID)
	 * @param paraMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptData(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 查询 支出项目基本信息(根据code 匹配ID)
	 * @param paraMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPayItemData(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 校验数据是否已存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException ;

	/**
	 * 查询其他费用预算执行统计（资金支出控制使用）
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<BudgExpense> queryBudgOtherExpenseCount(Map<String, Object> entityMap) throws DataAccessException ;
	public List<BudgExpense> queryBudgOtherExpenseCount(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException ;

	/**
	 * 计算其他费用预算执行统计（资金支出控制使用）
	 * @param mapVo
	 */
	public void countBudgOtherExpense(Map<String, Object> entityMap) throws DataAccessException ;
	
	
}
