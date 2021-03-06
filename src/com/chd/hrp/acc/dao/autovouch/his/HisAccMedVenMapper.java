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
import com.chd.hrp.acc.entity.autovouch.HisAccMedVen;

public interface HisAccMedVenMapper extends SqlMapper {

	/**
	 * 添加his药品供应商
	 * 
	 * */
	public int addHisAccMedVen(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除his药品供应商
	 * 
	 * */
	public int deleteHisAccMedVen(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 修改his药品供应商
	 * 
	 * */
	public int updateHisAccMedVen(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 *根据编码查询his药品供应商
	 * 
	 * */
	public HisAccMedVen queryHisAccMedVenByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 插叙his药品供应商不分页
	 * 
	 * */
	public List<HisAccMedVen> queryHisAccMedVen(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询his药品供应商分页
	 * 
	 * */
	public List<HisAccMedVen> queryHisAccMedVen(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 药品供应商打印
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryHisAccMedVenPrint(Map<String, Object> entityMap);

}
