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
import com.chd.hrp.acc.entity.autovouch.HisAccMedType;

public interface HisAccMedTypeMapper extends SqlMapper {

	/**
	 * 添加his药品类别
	 * 
	 * */
	public int addHisAccMedType(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除his药品类别
	 * 
	 * */
	public int deleteHisAccMedType(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 修改his药品类别
	 * 
	 * */
	public int updateHisAccMedType(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 *根据编码查询his药品类别
	 * 
	 * */
	public HisAccMedType queryHisAccMedTypeByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 插叙his药品类别不分页
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccMedType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询his药品类别分页
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccMedType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}
