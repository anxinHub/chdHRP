/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.service.balance;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 050804 资产帐表_专用 
 * @Table:
 * ASS_CARD_OTHER
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface AssBalanceGeneralService extends SqlService {

	
	/**
	 * @Description 
	 * 查询结果集<BR> 固定资产总账对账
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryBalanceAccountGeneralMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR> 固定资产明细账对账
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryBalanceAccountGeneralDetail(Map<String,Object> entityMap) throws DataAccessException;

}
