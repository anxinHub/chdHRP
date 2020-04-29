package com.chd.hrp.hr.service.sc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sc.HrTableDesign;

/**
 * 
 * @ClassName: HrTableDesignService 
 * @Description: 查询设计器
 * @author zn 
 * @date 2017年10月17日 下午4:14:31 
 * 
 *
 */
public interface HrTableDesignService{

	HrTableDesign queryHrTableDesignByCode(Map<String, Object> entityMap) throws DataAccessException;

	String addHrTableDesign(Map<String, Object> entityMap) throws DataAccessException;

	String updateHrTableDesign(Map<String, Object> entityMap) throws DataAccessException;

	String deleteHrTableDesign(Map<String, Object> entityMap) throws DataAccessException;

	String queryHrTableDesignTree(Map<String, Object> entityMap) throws DataAccessException;

	Map<String, String> generateSqlStatement(Map<String, Object> entityMap) throws DataAccessException;

	String updateDesignQueryColByCode(Map<String, Object> entityMap) throws DataAccessException;

	String queryDesignQueryColByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	String queryDesignQueryPageByCode(Map<String, Object> entityMap) throws DataAccessException;

	String queryHrTableDesign(Map<String, Object> entityMap) throws DataAccessException;
	
	String queryHrTableDesignExport(Map<String, Object> entityMap) throws DataAccessException;

	String addBatchTableDesign(List<HrTableDesign> list) throws DataAccessException;

	String queryDBTableTree(Map<String, Object> entityMap) throws DataAccessException;

	String updateDesignQuerySqlByCode(Map<String, Object> entityMap) throws DataAccessException;

	String updateDesignQueryPageByCode(Map<String, Object> entityMap) throws DataAccessException;

	Map<String, Object> genDesignQueryPage(Map<String, Object> entityMap) throws DataAccessException;

	String queryHrStatisticTableTree(Map<String, Object> entityMap) throws DataAccessException;

	String saveHrStatisticDesign(Map<String, Object> entityMap) throws DataAccessException;
	
}
