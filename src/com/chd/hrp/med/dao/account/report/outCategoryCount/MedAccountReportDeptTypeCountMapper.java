package com.chd.hrp.med.dao.account.report.outCategoryCount;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 
 * 出库分类统计:科室类型统计-查询表
 * @Table: 无针对表
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedAccountReportDeptTypeCountMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 科室类型统计 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAccountReportDeptTypeCount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室类型统计 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedAccountReportDeptTypeCount(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室类型统计 查询动态列表头<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryDeptTypeCountHead(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 科室类型统计 查询动态列表头<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryDeptTypeCountHeadNew(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取科室编码
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryDeptTypeCountHeadByCode(Map<String,Object> entityMap) throws DataAccessException;
}
