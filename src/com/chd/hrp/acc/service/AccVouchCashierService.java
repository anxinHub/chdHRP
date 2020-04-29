/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 凭证主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchCashierService {

	/**
	 * @Description 
	 * 凭证主表<BR> 查询AccVouch出纳相关数据分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	public String queryAccVouchCashier(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String updateBatchAccVouchCashier(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
}
