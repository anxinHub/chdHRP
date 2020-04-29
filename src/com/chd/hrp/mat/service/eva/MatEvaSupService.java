package com.chd.hrp.mat.service.eva;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MatEvaSupService {
	
	// 供应商评价-供应商信息查询
	public String queryMatEvaSupInfoLeft(Map<String, Object> mapVo) throws DataAccessException;
	// 供应商评价-评价查询
	public String queryMatEvaSupMainRight(Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String, Object>> queryMatEvaSupMainRightByCode(Map<String, Object> mapVo) throws DataAccessException;
	// 供应商评价-改变评价状态
	public String changeMatEvaSupState(Map<String, Object> mapVo) throws DataAccessException;
	// 供应商评价-删除评价
	public String deleteMatEvaSup(Map<String, Object> mapVo) throws DataAccessException;
	// 供应商评价-评价指标查询
	public String queryMatEvaSupTarget(Map<String, Object> mapVo) throws DataAccessException;
	// 供应商评价-扣分项查询
	public String queryMatEvaSupTargetDeduct(Map<String, Object> mapVo) throws DataAccessException;
	
	public String queryMatEvaSupTargetForAdd(Map<String, Object> mapVo) throws DataAccessException;
	// 供应商评价-添加或更新
	public String addOrUpdateMatEvaSup(Map<String, Object> mapVo) throws DataAccessException;
	
	public String addMatEvaTargetDudect(Map<String, Object> mapVo) throws DataAccessException;
	// 标度查询
	public String queryMatEvaTargetScale(Map<String, Object> mapVo) throws DataAccessException;
	// 扣分项查询
	public String queryMatEvaTargetDeduct(Map<String, Object> mapVo) throws DataAccessException;
	
}
