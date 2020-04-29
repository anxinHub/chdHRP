package com.chd.hrp.mat.dao.account.report.outCategoryCount;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 出库分类统计:科室统计-查询表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAccountReportDeptCountToFimMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 科室统计出库金额 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAccountReportDeptCount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室统计出库金额 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMatAccountReportDeptCount(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室统计 查询动态列表头<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryDeptCountHead(Map<String,Object> entityMap) throws DataAccessException;
}
