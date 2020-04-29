/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccLederCheck;

/**
* @Title. @Description.
* 财务辅助账表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccLederCheckService {

	/**
	 * @Description 
	 * 财务辅助账表<BR> 导入AccLederCheck
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccLederCheck(Map<String,Object> entityMap)throws DataAccessException;

	
}
