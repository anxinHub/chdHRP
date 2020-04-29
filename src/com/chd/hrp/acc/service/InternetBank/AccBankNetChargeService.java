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

public interface AccBankNetChargeService {
	
	/**
	 * @Description 查询材料付款
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccChargeApply(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccBankNetCharge(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccBankNetChargeRd(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 生成支付单
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String addAccBankNetCharge(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 补录银行信息
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updateAccBankNetCharge(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 补录银行信息
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String updateAccBankNetState(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public double sumTotalAmtByDay(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccBankNetChargeRdPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryAccBankNetChargeNum(Map<String, Object> entityMap) throws DataAccessException;
}
