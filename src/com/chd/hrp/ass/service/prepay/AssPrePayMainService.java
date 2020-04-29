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
 * @Description: 预付款主表
 * @Table: ASS_PRE_PAY_MAIN
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
public interface AssPrePayMainService extends SqlService {

	/**
	 *  预付发票主表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAssPreBill(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 *  预付发票明细表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryAssPreBillDetail(Map<String, Object> entityMap)throws DataAccessException;
	
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
	//预付款模板打印
	Map<String, Object> queryAssPrePayMainDY(Map<String, Object> map)
			throws DataAccessException;

	/**
	 * 取消确认操作
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String updateCancelConfirmPrePay(List<Map<String, Object>> entityMap)throws DataAccessException;

	List<String> queryAssPrepayState(Map<String, Object> mapVo)throws DataAccessException;


}
