package com.chd.hrp.budg.dao.project.begin;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgProjYear;

public interface BudgProjYearMapper  extends SqlMapper {

	public   int queryBudgProjYear(List<Map<String, Object>> listVo) throws DataAccessException;

	public void addBudgProjBegainRemark(Map<String, Object> entityMap) throws DataAccessException;


	public Map<String, Object> queryByProjIdOrSourceId(Map<String, Object> entityMap);


	public List<Map<String, Object>> queryR(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryRDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	public int updateBatcht(List<Map<String, Object>> tlist) throws DataAccessException;
	public  int updateBatchDetailt(List<Map<String, Object>> tlist) throws DataAccessException;
	public int queryProjYear(Map<String, Object> entityMap) throws DataAccessException;
	public int queryProjYearDetail(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryTR(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryTRDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	public int updateBatchtr(List<Map<String, Object>> trlist) throws DataAccessException;

	public int updateBatchtrDetail(List<Map<String, Object>> trlist) throws DataAccessException;

	public int updateProjBegainRemark(Map<String, Object> entityMap) throws DataAccessException;
}
