/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.proj;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 财政项目收入预算  / 财政项目收入预算 执行
 * @Table:
 * BUDG_PROJ_YEAR
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgFinancProjService extends SqlService {
	
	/**
	 * 财政项目收入预算 查询   
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgFinancProjIncome( Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 财政项目收入预算执行 查询 
	 * @param entityMap
	 * @return
	 */
	public String queryBudgFinancProjIncomeExe(Map<String, Object> entityMap);

}
