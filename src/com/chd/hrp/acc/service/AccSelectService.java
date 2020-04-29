package com.chd.hrp.acc.service;

import java.util.Map;

/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
import org.springframework.dao.DataAccessException;
/**
* @Title. @Description.
* 财务管理系统下拉框<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface AccSelectService {

		//查询行业性质
		public String queryHosNatureDict(Map<String,Object> entityMap) throws DataAccessException;

		//查询医院	
		public String queryHosInfoDict(Map<String,Object> entityMap) throws DataAccessException;
		
		//查询账套	
		public String queryCopyDict(Map<String,Object> entityMap) throws DataAccessException;
			
		//查询会计年度	
		public String queryAcctYearDict(Map<String,Object> entityMap) throws DataAccessException;
	
}
