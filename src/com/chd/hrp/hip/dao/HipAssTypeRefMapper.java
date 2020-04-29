/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.hip.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hip.entity.HipAssTypeRef;

public interface HipAssTypeRefMapper extends SqlMapper {

	/**
	 * 添加
	 * 
	 * */
	public int addHipAssTypeRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 添加
	 * 
	 * */
	public int addBatchHipAssTypeRef(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 删除
	 * 
	 * */
	public int deleteHipAssTypeRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 * 批量删除
	 * */
	public int deleteBatchHipAssTypeRef(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 根据编码查询
	 * 
	 * */
	public HipAssTypeRef queryHipAssTypeRefByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 插叙患者类别不分页
	 * 
	 * */
	public List<HipAssTypeRef> queryHipAssTypeRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询患者类别分页
	 * 
	 * */
	public List<HipAssTypeRef> queryHipAssTypeRef(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}
