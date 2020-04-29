package com.chd.hrp.mat.dao.account.report;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 科室出库查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAccountReportDeptOutSearchMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 科室出库查询表 查询<BR>   
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatAccountReportDeptOutSearch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室出库查询表 分页查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatAccountReportDeptOutSearch(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryMatAccountReportDeptOutSearchByCollect(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatAccountReportDeptOutSearchByCollect(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
}
