/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.dao.account.balance;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 库存药品收发帐表(虚仓)
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface MedAccountBalanceVirStockInvBalanceMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedAccountBalanceVirStockInvBalance(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询报表<BR> 分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedAccountBalanceVirStockInvBalance(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
}