/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;



/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface HosDictService {

	
	public String queryHosDictBySelector(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 用于集团化管理   集团单位选择器
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryGroupHosDictBySelector(Map<String,Object> entityMap)throws DataAccessException;
	
}
