/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgincome.carry;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Description:
 * 医疗收入预算结转
 * @Table:
 * BUDG_WORK_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgIncomeCarryService {
	
	/**
	 *结转
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String chargeBudgIncomeCarry(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 反结
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String reChargeBudgIncomeCarry(Map<String, Object> entityMap) throws DataAccessException;

	
	/**
	 * 查询 已结转年月、待结转年月
	 * @param mapVo
	 * @return
	 */
	public String queryYearMonth(Map<String, Object> entityMap) throws DataAccessException;


}
