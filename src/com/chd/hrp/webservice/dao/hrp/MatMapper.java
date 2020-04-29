package com.chd.hrp.webservice.dao.hrp;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatMapper extends SqlMapper{

	public List<Map<String,Object>> queryHosSupDict(Map<String,Object> map) throws  DataAccessException;
	
	public List<Map<String,Object>> queryMatOrderMainByOrderId(Map<String,Object> map) throws  DataAccessException;
	
	public List<Map<String,Object>> queryMatOrderDetailByOrderId(Map<String,Object> map) throws  DataAccessException;
	
	//根据集团医院账套查询云医院ID（hrp云平台）
	public String queryCHosId(Map<String,Object> map) throws  DataAccessException;
	
}
