/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.dao.autovouch.his;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.autovouch.HisAccMedStoreRef;

public interface HisAccMedStoreRefMapper extends SqlMapper {

	/**
	 * 添加his药库药房
	 * 
	 * */
	public int addHisAccMedStoreRef(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 删除his药库药房
	 * 
	 * */
	public int deleteHisAccMedStoreRef(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 修改his药库药房
	 * 
	 * */
	public int updateHisAccMedStoreRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 *根据编码查询his药库药房
	 * 
	 * */
	public HisAccMedStoreRef queryHisAccMedStoreRefByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 插叙his药库药房不分页
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccMedStoreRef(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询his药库药房分页
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccMedStoreRef(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}
