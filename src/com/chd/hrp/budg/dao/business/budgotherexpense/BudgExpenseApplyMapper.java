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
import com.chd.hrp.budg.entity.BudgExpenseApply;
/**
 *  
 * @Description:
 * 费用申报 
 * @Table:
 * BUDG_EXPENSE_APPLY
 * @Author: slient
 * @email:  slient@e-tonggroup.com
 * @Version: 1.0
 */
public interface BudgExpenseApplyMapper extends SqlMapper{

	/**
	 * 支出项目下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgPaymentItem(Map<String, Object> mapVo)throws DataAccessException;
	public List<Map<String, Object>> queryBudgPaymentItem(Map<String, Object> mapVo, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 申报人员 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryEmpDict(Map<String, Object> mapVo)throws DataAccessException;
	public List<Map<String, Object>> queryEmpDict(Map<String, Object> mapVo, RowBounds rowBounds)throws DataAccessException;
	
	public Map<String, Object> queryEmpDictByCode(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 归口科室 下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgCostDutyDept(Map<String, Object> mapVo, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 更新状态
	 * @param budgExpenseApply
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBudgExpenseApplyState(BudgExpenseApply budgExpenseApply)throws DataAccessException;
	/**
	 * 审核、消审
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public int auditOrUnAudit(List<Map<String, Object>> listVo) throws DataAccessException;
	public int affiOrUnAffi(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据 所传参数 查询数据状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryBudgExpenseApplyState(Map<String, Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> queryBudgPaymentItemByCode(Map<String, Object> mapVo)throws DataAccessException;
	public Map<String, Object> queryBudgDeptByCode(Map<String, Object> mapVo)throws DataAccessException;
	public Map<String, Object> queryBudgEmpByCode(Map<String, Object> mapVo)throws DataAccessException;
	
	
}
