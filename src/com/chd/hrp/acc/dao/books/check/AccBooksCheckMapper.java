package com.chd.hrp.acc.dao.books.check;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface AccBooksCheckMapper extends SqlMapper {
	
	/**
	 * 总查询(存储过程中进行分支)
	 * */
	public  List<Map<String, Object>> queryAccBooksCheck(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	* 交叉表查询表头
	*/
	public List<Map<String, Object>> querySubjHeadByJCB(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 根据核算项获取核算项信息
	 */
	public Map<String, Object> queryCheckTypeByCheck(Map<String, Object> mapVo) throws DataAccessException;
}
