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
 * 费用申报 
 * @Table:
 * BUDG_EXPENSE_APPLY
 * @Author: slient
 * @email:  slient@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgExpenseApplyService extends SqlService {
	
	/**
	 * 支出项目 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryBudgPaymentItem(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 申报人员 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryEmpDict(Map<String, Object> mapVo)throws DataAccessException;
	
	Map<String, Object> queryEmpDictByCode(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 归口科室 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryBudgCostDutyDept(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 查询费用申报明细
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryBudgExpApplyDetail(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 审核、消审
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String auditOrUnAudit(List<Map<String, Object>> listVo) throws DataAccessException;
	
	public String affiOrUnAffi(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 根据 所传参数 查询数据状态
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryBudgExpenseApplyState(Map<String, Object> page) throws DataAccessException;
	
	public Map<String, Object> queryBudgPaymentItemByCode(Map<String, Object> mapVo)throws DataAccessException;
	public Map<String, Object> queryBudgDeptByCode(Map<String, Object> mapVo)throws DataAccessException;
	public Map<String, Object> queryBudgEmpByCode(Map<String, Object> mapVo)throws DataAccessException;

}
