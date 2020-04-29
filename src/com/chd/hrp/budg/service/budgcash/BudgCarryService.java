/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.budgcash;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 资金预算结转
 * @Table:
 * BUDG_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgCarryService {
	
	/**
	 * 待结转月份查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>>  queryYearMonth(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 已结转月份查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>>  queryYearMonthBefore(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 *结转
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String chargeBudgWork(Map<String,Object> mapVo) throws DataAccessException;
	
	/**
	 * 反结
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String reChargeBudgWork(Map<String, Object> mapVo) throws DataAccessException;

	
	/**
	 *启用  年月查询
	 * @param mapVo
	 * @return
	 */
	public String queryYearMonthStart(Map<String, Object> mapVo) throws DataAccessException;


}
