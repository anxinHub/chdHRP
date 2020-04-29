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
import com.chd.hrp.hip.entity.HipAssRef;

public interface HipAssRefMapper extends SqlMapper {

	/**
	 * 添加
	 * 
	 * */
	public int addHipAssRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 添加
	 * 
	 * */
	public int addBatchHipAssRef(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 删除
	 * 
	 * */
	public int deleteHipAssRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 * 批量删除
	 * */
	public int deleteBatchHipAssRef(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 根据编码查询
	 * 
	 * */
	public HipAssRef queryHipAssRefByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 不分页
	 * 
	 * */
	public List<HipAssRef> queryHipAssRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 分页
	 * 
	 * */
	public List<HipAssRef> queryHipAssRef(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}
