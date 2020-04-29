/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.dao.autovouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.autovouch.AccBusiMeta;
import com.chd.hrp.acc.entity.autovouch.AccBusiTemplate;

public interface AccBusiTemplateMapper extends SqlMapper {

	/**
	 * 自动凭证模板 不分页
	 * 
	 * */
	public List<AccBusiTemplate> queryAccBusiTemplate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 自动凭证模板 分页
	 * 
	 * */
	public List<AccBusiTemplate> queryAccBusiTemplate(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 根据编码查询
	 * 
	 * */
	public AccBusiTemplate queryAccBusiTemplateByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据编码查询
	 * 
	 * */
	public int addAccBusiTemplate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据编码查询
	 * 
	 * */
	public int updateAccBusiTemplate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除模板明细
	 * 
	 * */
	public int deleteBatchAccBusiTemplate(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 自动凭证模板 不分页
	 * 
	 * */
	public List<Map<String, Object>> queryAccBusiRelaForMetaCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询科目 不分页
	 * 
	 * */
	public List<Map<String, Object>> queryAccBusiRelaForAccSubj(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据编码查询
	 * 
	 * */
	public AccBusiMeta queryAccBusiMetaByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccBusiHosStore(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccBusiHosResource(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAccBusiRelaForStoreAutoSet(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccSubjForAutoSet(Map<String, Object> map) throws DataAccessException;
	
	public int deleteAccBusiMapByStore(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list)throws DataAccessException;
	public int addAccBusiMapByStore(@Param(value="map")Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list)throws DataAccessException;
}
