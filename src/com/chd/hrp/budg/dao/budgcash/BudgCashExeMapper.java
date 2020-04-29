/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgcash;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 现金存量预算
 * @Table:
 * BUDG_CASH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgCashExeMapper extends SqlMapper{
	
	/**
	 * 查询查询指定月份数据是否已经结账
	 * @param item
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgCarry(Map<String, Object> item) throws DataAccessException;
	
}
