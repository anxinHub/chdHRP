/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.service.InternetBank;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Title. @Description.<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AccBankNetAssPayService {
	
	/**
	 * @Description 查询材料付款
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAssPayMain(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccBankNetAssPay(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccBankNetAssPayRd(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 生成支付单
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String addAccBankNetAssPay(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 补录银行信息
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updateAccBankNetAssPay(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 查询
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public double sumTotalAmtByDay(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryMatPayMainPrint(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccBankNetAssPayRdPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryAccBankNetAssPayNum(Map<String, Object> entityMap) throws DataAccessException;
}
