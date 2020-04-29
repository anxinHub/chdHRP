/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.budgotherexpense;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 费用定额 
 * @Table:
 * BUDG_EXP_FIX_AMOUNT
 * @Author: slient
 * @email:  slient@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgExpFixAmountService extends SqlService {
	
	/**
	 * 支出项目 下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgPaymentItem(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 预算科室 下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDeptSelect(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 查询预算科室列表  添加页面
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDept(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 校验数据 是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;

}
