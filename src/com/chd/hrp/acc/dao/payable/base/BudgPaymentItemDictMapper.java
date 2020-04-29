/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.dao.payable.base;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.payable.BudgPaymentItemDict;
/**
 * 
 * @Description:
 * 控制方式
 * @Table:
 * BUDG_PAYMENT_ITEM_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface BudgPaymentItemDictMapper extends SqlMapper{
	public Long queryPaymentItemDictSequence();	
	
	public int updateIsLast(Map<String,Object> entityMap);
	
	public int updateIsFresh(Map<String,Object> entityMap);

	/**
	 * 查用户读/写权限的支出项目
	 */
	public List<BudgPaymentItemDict> queryByIsWriteOrIsRead(Map<String, Object> paraMap);
	
}
