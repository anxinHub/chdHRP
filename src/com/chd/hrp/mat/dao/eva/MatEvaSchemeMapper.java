package com.chd.hrp.mat.dao.eva;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatEvaSchemeMapper extends SqlMapper {
	
	//评价方案查询
	public List<Map<String, Object>> queryMatEvaScheme(Map<String, Object> map) throws DataAccessException;
	//评价方案指标查询
	public List<Map<String, Object>> queryMatEvaSchemeDetail(Map<String, Object> map) throws DataAccessException;
	//保存主表数据
	public int addMatEvaScheme(Map<String, Object> mapVo) throws DataAccessException;
	//引入指标数据
	public int addMatEvaSchemeDetail(Map<String, Object> mapVo) throws DataAccessException;
	//更新指标数据
	public int updateMatEvaSchemeDetail(List<Map<String, Object>> detailList) throws DataAccessException;
	//更新主表分值
	public int updateMatEvaScheme(Map<String, Object> mapVo)throws DataAccessException;
}
