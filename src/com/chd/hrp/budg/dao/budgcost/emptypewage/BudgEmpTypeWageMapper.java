/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgcost.emptypewage;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 *  科室职工平均工资
 * @Table:
 * BUDG_EMP_TYPE_WAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgEmpTypeWageMapper extends SqlMapper{
	/**
	 * 查询工资项目
	 */
	public List<Map<String, Object>> queryBudgWageItem(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String, Object> queryDataByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询科室职工工资总表数据  并汇总计算后插入平均工资表中
	 * @param mapVo
	 */
	public void insertDataFromTotal(Map<String, Object> mapVo) throws DataAccessException;
	
}
