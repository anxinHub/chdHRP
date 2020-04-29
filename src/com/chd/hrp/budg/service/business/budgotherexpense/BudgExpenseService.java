/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.budgotherexpense;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface BudgExpenseService extends SqlService {
	
	/**
	 * 根据主键查询数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDataByCode(Map<String, Object> mapVo) throws DataAccessException;

	public String generateBudgExpense(Map<String, Object> mapVo) throws DataAccessException;
	
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
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgOtherExpenseCount(Map<String, Object> mapVo) throws DataAccessException ;

	/**
	 * 计算其他费用预算执行统计（资金支出控制使用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String countBudgOtherExpense(Map<String, Object> mapVo) throws DataAccessException ;

}
