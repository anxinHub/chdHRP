/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.account.balance;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description:
 * 材料收发结存表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MatAccountBalanceInvHoldService {
	
	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatAccountBalanceInvHold(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 报表打印<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatAccountBalanceInvHoldPrint(Map<String,Object> entityMap) throws DataAccessException;
}
