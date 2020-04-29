package com.chd.hrp.htcg.dao.info;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.info.HtcgIdentityType;

public interface HtcgIdentityTypeMapper extends SqlMapper{
	
	/**
	 * 添加医保类型
	 * */
	public int addHtcgIdentityType(Map<String, Object> entityMap) throws DataAccessException;
	
	public int addBatchHtcgIdentityType(List<Map<String, Object>> list) throws DataAccessException;
	/**
	 * 删除医保类型
	 * */
	public int deleteHtcgIdentityType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除医保类型
	 * */
	public int deleteBatchHtcgIdentityType(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 修改医保类型
	 * */
	public int updateHtcgIdentityType(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询所有的医保类型
	 * */
	public List<HtcgIdentityType> queryHtcgIdentityType(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<HtcgIdentityType> queryHtcgIdentityType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询所有的医保类型BYcode
	 * */
	public HtcgIdentityType queryHtcgIdentityTypeByCode(Map<String, Object> entityMap) throws DataAccessException;
	
}
