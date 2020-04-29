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
import com.chd.hrp.acc.entity.autovouch.HisAccMedPayType;

public interface HisAccMedPayTypeMapper extends SqlMapper {

	/**
	 * 添加his药品付款方式 
	 * 
	 * */
	public int addHisAccMedPayType(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除his药品付款方式 
	 * 
	 * */
	public int deleteHisAccMedPayType(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 修改his药品付款方式 
	 * 
	 * */
	public int updateHisAccMedPayType(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 *根据编码查询his药品付款方式
	 * 
	 * */
	public HisAccMedPayType queryHisAccMedPayTypeByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 插叙his药品付款方式不分页
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccMedPayType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询his药品付款方式分页
	 * 
	 * */
	public List<Map<String, Object>> queryHisAccMedPayType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}
