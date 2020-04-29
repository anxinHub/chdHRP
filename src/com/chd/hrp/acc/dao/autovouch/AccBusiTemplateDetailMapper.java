/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.dao.autovouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.autovouch.AccBusiTemplateDetail;

public interface AccBusiTemplateDetailMapper extends SqlMapper {

	/**
	 * 自动凭证模板 不分页
	 * 
	 * */
	public List<AccBusiTemplateDetail> queryAccBusiTemplateDetail(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 自动凭证模板 分页
	 * 
	 * */
	public List<AccBusiTemplateDetail> queryAccBusiTemplateDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * 财务自动凭证模板 不分页
	 * 
	 * */
	public List<AccBusiTemplateDetail> queryAccBusiTemplateDetailAcc(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 财务自动凭证模板 分页
	 * 
	 * */
	public List<AccBusiTemplateDetail> queryAccBusiTemplateDetailAcc(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 预算自动凭证模板 不分页
	 * 
	 * */
	public List<AccBusiTemplateDetail> queryAccBusiTemplateDetailBudg(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 预算自动凭证模板 分页
	 * 
	 * */
	public List<AccBusiTemplateDetail> queryAccBusiTemplateDetailBudg(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * 批量添加模板明细
	 * 
	 * */
	public int addBatchAccBusiTemplateDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 删除模板明细
	 * 
	 * */
	public int deleteBatchAccBusiTemplateDetail(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	//根据模板编码查询凭证模板
	public List<Map<String, Object>> queryAccBusiTemplateByCode(Map<String, Object> entityMap)throws DataAccessException;
	

}
