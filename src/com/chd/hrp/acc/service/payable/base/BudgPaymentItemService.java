/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.service.payable.base;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.acc.entity.payable.BudgPaymentItem;
/**
 * 
 * @Description:
 * 控制方式
 * @Table:
 * BUDG_PAYMENT_ITEM
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgPaymentItemService extends SqlService {
	public BudgPaymentItem queryPaymentItemBySuperCode(Map<String,Object> entityMap);

	public String queryBySuperCode(Map<String, Object> entityMap)	throws DataAccessException;

	public Long queryPaymentItemSequence() throws DataAccessException;

	public Long queryPaymentItemDictSequence() throws DataAccessException;
	public void prcUpdateBudgItemALL(Map<String,Object> entityMap) throws DataAccessException ;

}
