package com.chd.hrp.mat.service.outsum;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


/**
 *@author Fan Yang
 *@date   2020年4月21日
 *@email   yangfan1105@dhcc.com.cn
 */
public interface MatOutSumService {
	
	public String queryOutSumSingleSum(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String, Object>> queryOutSumSingleSumPrint(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryDeptStoreCross(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String, Object>> queryDeptStoreCrossPrint(Map<String, Object> mapVo) throws DataAccessException;
	
}
