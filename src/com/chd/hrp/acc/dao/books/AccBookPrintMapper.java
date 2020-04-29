/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.books;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 账簿方案
* @Author: 
*/


public interface AccBookPrintMapper extends SqlMapper{
	
	//查询科目列表
	public List<Map<String, Object>> queryAccBooksPrintSubj(Map<String,Object> entityMap)throws DataAccessException;
	
	//查询打印结果
	public List<Map<String, Object>> collectAccBooksPrint(Map<String, Object> entityMap)throws DataAccessException;
}
