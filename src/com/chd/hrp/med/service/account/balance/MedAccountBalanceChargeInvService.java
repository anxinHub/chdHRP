/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.account.balance;
import java.util.*;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description:
 * 计费药品使用查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedAccountBalanceChargeInvService {
	
	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedAccountBalanceChargeInv(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 汇总查询报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedAccountBalanceChargeInvCollect(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 按住院号汇总查询报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedAccountBalanceChargeInvCollectByHospital(Map<String,Object> entityMap) throws DataAccessException;
	
}