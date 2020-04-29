/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgincome.reportforms.monitoring;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 医院医疗收入预算执行监控
 * @Table:
 * BUDG_ELSE_INCOME_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedElseIncomeExeMapper extends SqlMapper{
	
	/**
	 * 查询 所查 预算年度 是否有 执行数据 ( flag 标识 (-1:表示 所查预算年度 没有执行数据) 解决  没有执行数据，前台合计行 展现数据 窜行问题)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryExecuteDataExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 所查 预算年度 是否有 预算数据 ( budg_flag 标识 (-1:表示 所查预算年度 没有预算数据) 解决  没有预算数据，前台合计行 展现数据 窜行问题)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBudgDataExist(Map<String, Object> entityMap) throws DataAccessException;
	
}
