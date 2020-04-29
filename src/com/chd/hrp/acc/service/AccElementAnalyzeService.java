package com.chd.hrp.acc.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccElementAnalyzeService {

	//查询
	String queryElements(Map<String, Object> mapVo);
	List queryElementForUpdata(Map<String, Object> mapVo);
	//打印的查询方法
	List<Map<String, Object>> queryAccElementPrint(Map<String,Object> entityMap) throws DataAccessException;


	//添加
	String addElements(Map<String, Object> mapVo);
	
	//添加
	String updataElements(Map<String, Object> mapVo);

	//删除
	String deleteElements(Map<String, Object> mapVo);
}
