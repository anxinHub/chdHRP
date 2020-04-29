/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.initial;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 期初记账
 * @Table:
 * MAT_IN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatInitChargeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 仓库是否已期初记账，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsStoreIsAccount(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 仓库是否含有未确认的期初入库单据，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsInitNotConfirmBill(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 仓库是否含有非期初入库的单据，0为否、其他值为是<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsNotInitBill(Map<String, Object> entityMap)throws DataAccessException;
}
