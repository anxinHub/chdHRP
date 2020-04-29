package com.chd.hrp.mat.service.eva;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MatEvaTargetService {
	//指标类别查询(返回树形结构的json格式)
	public String queryMatEvaTargetTypeTree(Map<String, Object> map) throws DataAccessException;
	//指标分类编码规则查询
	public String queryMatEvaTargetTypeRules(Map<String, Object> map) throws DataAccessException;
	//指标类别保存
	public Map<String, Object> saveMatEvaTargetType(Map<String, Object> map) throws DataAccessException;
	//指标类别删除
	public Map<String, Object> deleteMatEvaTargetType(Map<String, Object> map) throws DataAccessException;
	
	//标准标度查询
	public String queryMatEvaScaleList(Map<String, Object> map) throws DataAccessException;
	//标准标度保存
	public Map<String, Object> saveMatEvaScale(Map<String, Object> map) throws DataAccessException;
	//标准标度删除
	public Map<String, Object> deleteMatEvaScale(Map<String, Object> map) throws DataAccessException;
	
	//指标查询
	public String queryMatEvaTargetList(Map<String, Object> map) throws DataAccessException;
	//指标保存
	public Map<String, Object> saveMatEvaTarget(Map<String, Object> map) throws DataAccessException;
	//指标删除
	public Map<String, Object> deleteMatEvaTarget(Map<String, Object> map) throws DataAccessException;
	//指标导入
	public Map<String, Object> addMatEvaTargetByImp(Map<String, Object> map) throws DataAccessException;
	
	//指标标度查询
	public String queryMatEvaTargetScaleList(Map<String, Object> map) throws DataAccessException;
	//指标标度保存
	public Map<String, Object> saveMatEvaTargetScale(Map<String, Object> map) throws DataAccessException;
	//指标标度删除
	public Map<String, Object> deleteMatEvaTargetScale(Map<String, Object> map) throws DataAccessException;
	//指标标度引用标准标度
	public Map<String, Object> addMatEvaTargetScaleByBZ(Map<String, Object> map) throws DataAccessException;
	//批量设置指标标度保存
	public Map<String, Object> saveMatEvaTargetScaleBatch(Map<String, Object> map) throws DataAccessException;
	
	//指标扣分项查询
	public String queryMatEvaTargetDeductList(Map<String, Object> map) throws DataAccessException;
	//指标扣分项保存
	public Map<String, Object> saveMatEvaTargetDeduct(Map<String, Object> map) throws DataAccessException;
	//指标扣分项删除
	public Map<String, Object> deleteMatEvaTargetDeduct(Map<String, Object> map) throws DataAccessException;
}
