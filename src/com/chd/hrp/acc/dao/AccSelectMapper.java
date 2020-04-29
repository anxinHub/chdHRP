/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


/**
* @Title. @Description.
* 财务管理系统下拉框<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/

public interface AccSelectMapper  extends SqlMapper{

	//查询行业性质
	public List<Map<String, Object>> queryHosNatureDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	//查询医院	
	public List<Map<String, Object>> queryHosInfoDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//查询账套	
	public List<Map<String, Object>> queryCopyDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
		
	//查询会计年度	
	public List<Map<String, Object>> queryAcctYearDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryCheckTypeDict(Map<String, Object> mapVo,RowBounds rowBounds) throws DataAccessException;
	//查询工资套下拉框并且不显示当前工资套
	public List<Map<String, Object>> queryWageAndNotInThisWage(Map<String, Object> mapVo);
}
