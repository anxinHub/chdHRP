package com.chd.hrp.sys.dao.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccYearMonth;


public interface SysBaseMapper extends SqlMapper {
	/** 
	 * @Description 
	 * 获取编码规则<BR> 
	 * @param  entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	*/
	public Map<String,Object> getHosRules(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 获取编码规则<BR> 
	 * @param  entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> getHosRulesList(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryGroupParaList(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String,Object>> queryGroupSFList(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取会计期间信息<BR> 
	 * @param  entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	*/
	public List<AccYearMonth> queryAccYearMonth(Map<String,Object> entityMap)throws DataAccessException;
}
