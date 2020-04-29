/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.budgeworkcarry;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Description:
 * 业务预算结转
 * @Table:
 * BUDG_WORK_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkCarryService {
	
	/**
	 * 待结转月份查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String  queryYearMonth(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 *结转
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String chargeBudgWorkCarry(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 反结
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String reChargeBudgWorkCarry(Map<String, Object> entityMap) throws DataAccessException;



}
