/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.service.InternetBank;

import java.util.List; 
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Title. @Description. 现金流量标注<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AccBankNetPaymentService {
	
	/**
	 * @Description 查询材料付款
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccPaymentApply(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccBankNetPayment(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccBankNetPaymentRd(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 生成支付单
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String addAccBankNetPayment(Map<String, Object> entityMap) throws DataAccessException;
	 
	/**
	 * @Description 补录银行信息
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updateAccBankNetPayment(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public double sumTotalAmtByDay(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccPaymentApplyPrint(Map<String, Object> entityMap) throws DataAccessException; 
	
	public List<Map<String, Object>> queryAccBankNetPaymentRdPrint(Map<String, Object> entityMap) throws DataAccessException; 

	public String queryAccBankNetPaymentPayNum(Map<String, Object> entityMap) throws DataAccessException;
	 
}
