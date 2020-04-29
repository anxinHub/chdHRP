package com.chd.hrp.mat.dao.eva;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatEvaSupMapper extends SqlMapper{

	public List<Map<String, Object>> queryMatEvaSupInfoLeft(
			Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryMatEvaSupInfoLeft(
			Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryMatEvaSupMainRight(
			Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryMatEvaSupMainRight(
			Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	public int changeMatEvaSupState(
			Map<String, Object> mapVo) throws DataAccessException;

	public int deleteMatEvaSup(
			Map<String, Object> mapVo) throws DataAccessException;
	
	public int deleteMatEvaSupTarget(
			Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String, Object>> queryMatEvaSupTarget(
			Map<String, Object> mapVo) throws DataAccessException;
	
	public List<Map<String, Object>> queryMatEvaSupTargetForAdd(
			Map<String, Object> mapVo) throws DataAccessException;
	
	public int addMatEvaSupMain(
			Map<String, Object> mapVo) throws DataAccessException;
	
	public int updateMatEvaSupMain(
			Map<String, Object> mapVo) throws DataAccessException;
	
	public int addMatEvaSupTarget(
			List<Map<String, Object>> mapList) throws DataAccessException;
			
	public int updateMatEvaSupTarget(
			List<Map<String, Object>> mapList) throws DataAccessException;

	public List<Map<String, Object>> queryMatEvaTargetScale(
			Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryMatEvaTargetDeduct(
			Map<String, Object> mapVo) throws DataAccessException;

	public List<Map<String, Object>> queryMatEvaSupTargetDeduct(
			Map<String, Object> mapVo) throws DataAccessException;

	public int deleteMatEvaTargetDudect(
			Map<String, Object> mapVo) throws DataAccessException;

	public int addMatEvaTargetDudect(
			List<Map<String, Object>> detailList) throws DataAccessException;

	public int addMatEvaSupBillRela(
			Map<String, Object> mapVo) throws DataAccessException;
}
