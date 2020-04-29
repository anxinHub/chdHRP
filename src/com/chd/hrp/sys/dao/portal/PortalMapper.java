package com.chd.hrp.sys.dao.portal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface PortalMapper extends SqlMapper {
	
	/**
	 * 查询系统内置数据设置表 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> querySysPortalTitleSet(Map<String,Object> entityMap) throws DataAccessException ;

	/**
	 * 查询系统内置数据设置表 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querySysPortalTitleSet(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询用户数据设置表 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> querySysPortalTitleUser(Map<String,Object> entityMap) throws DataAccessException ;

	/**
	 * 查询用户数据设置表 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querySysPortalTitleUser(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * 查询门户栏目 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> querySysPortalTitle(Map<String,Object> entityMap) throws DataAccessException ;

	/**
	 * 查询门户栏目 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> querySysPortalTitle(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	public List<Map<String,Object>> executeViewSql(Map<String,Object> entityMap) throws DataAccessException ;
	
	public Long querySysPortalTitleUserCount(Map<String,Object> entityMap) throws DataAccessException ;
	
	
	public List<Map<String, Object>> queryOrgChartByGroup(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> queryOrgChartByHos(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> queryOrgChartByCopy(Map<String, Object> map) throws DataAccessException;

	public List<Map<String, Object>> queryOrgChartByDept(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> queryOrgChartByUser(Map<String, Object> map) throws DataAccessException;
}
