/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
  
package com.chd.hrp.ass.service.pay.main;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 
 * @Description:
 * state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR> 
 * @Table:
 * MAT_PAY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssPayForAccMainService {

	/**
	 * @Description 
	 * 查询结果集state 1:未审核；2审核；3:记帐 PAY_BILL_TYPE: 0付款 1 退款<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAssPayForAccMain(Map<String,Object> entityMap) throws DataAccessException;

	
	/**
	 * 审核、消审、确认、取消确认
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String updateAssPayState(List<Map<String, Object>> listVo) throws DataAccessException;
	
	
			
}
