/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.service.prepay;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

/**
 * 
 * @Description: 预退款主表
 * @Table: ASS_BACK_PRE_PAY_MAIN
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssBackPrePayMainService extends SqlService {

	/**
	 *  预退发票主表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAssBackPreBill(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 *  预付退票明细表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAssBackPreBillDetail(Map<String, Object> entityMap)throws DataAccessException;
	
	
	/**
	 *  支付方式下拉框
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAccPayType(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 确认操作
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateConfirmPrePay(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * 预退款模板打印
	 */
	Map<String, Object> queryAssBackPrePayMainDY(Map<String, Object> map)
			throws DataAccessException;

	List<String> queryAssBackPrepayState(Map<String, Object> mapVo)throws DataAccessException;

}
