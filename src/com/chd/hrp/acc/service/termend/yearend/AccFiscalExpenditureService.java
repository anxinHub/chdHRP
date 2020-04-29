/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.service.termend.yearend;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 财政基本补助支出结转<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccFiscalExpenditureService {

	/**
	 * @Description 
	 * 财政基本补助支出结转<BR> 添加凭证
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccFiscalExpenditureVouch(Map<String,Object> entityMap)throws DataAccessException;
}
