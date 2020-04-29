package com.chd.hrp.acc.dao;

import com.chd.base.SqlMapper;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AccElementAnalyzeMapper extends SqlMapper{

	//模糊查询
	List<Map<String, Object>> queryElements(Map<String, Object> mapVo);
	//精确查询
	List<Map<String, Object>> queryElementForUpdata(Map<String, Object> mapVo);
	//子级查询
	List<Map<String, Object>> querySonElements(Map<String, Object> mapVo);
	//打印查询
	List<Map<String, Object>> queryAccElementAnalysis(Map<String,Object> entityMap) throws DataAccessException;

	//添加
	int addElements(Map<String, Object> mapVo);
	//修改
	int updataElements(Map<String, Object> mapVo);

	//删除
	int deleteElements(Map<String, Object> mapVo);

}
