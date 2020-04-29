/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgcost.emptypewage;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 科室职工平均工资
 * @Table:
 * BUDG_EMP_TYPE_WAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgEmpTypeWageService extends SqlService {

	/**
	 * 查询工资项目
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgWageItem(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 根据主键查询数据信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDataByCode(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 计算数据 获取科室职工平均工资
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String collectBudgEmpWageTotal(Map<String, Object> mapVo) throws DataAccessException;

}
