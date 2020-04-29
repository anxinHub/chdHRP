package com.chd.hrp.med.dao.account.report.outCategoryCount;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 
 * 出库分类统计:药品分类统计
 * @Table: 无针对表
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MedAccountReportMedTypeCountMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 药品分类统计 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedTypeCount(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 药品分类统计 分页查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryMedTypeCount(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
}
