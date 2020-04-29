/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.med;
import java.util.Map;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 医疗支出执行数据
 * @Table:
 * BUDG_MED_COST_EXECUTE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedCostExecuteMapper extends SqlMapper{
	
	/**
	 * 医疗支出执行数据  采集时 （财务取数）  删除 采集年月 数据
	 * @param entityMap
	 * @return
	 */
	public int deleteMonthData(Map<String, Object> entityMap);
	
	/**
	 * 医疗支出执行数据  采集 （财务取数）
	 * @param entityMap
	 * @return
	 */
	public int collectData(Map<String, Object> entityMap);
	
}
