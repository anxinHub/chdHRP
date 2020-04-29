package com.chd.hrp.acc.service.accDictType;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface AccDictTypeService extends SqlService  {
	//查询
	String queryDict(Map<String, Object> mapVo);

	//添加
	String addDict(Map<String, Object> mapVo);

	//删除
	String deleteDict(Map<String, Object> mapVo);
	
	//添加
	String updateDict(Map<String, Object> mapVo);

	//打印
	List<Map<String, Object>> queryAccElementPrint(Map<String,Object> entityMap) throws DataAccessException;



}
