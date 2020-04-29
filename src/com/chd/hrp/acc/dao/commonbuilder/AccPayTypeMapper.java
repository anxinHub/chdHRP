/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.commonbuilder;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccPayType;

/**
* @Title. @Description.
* 结算方式<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccPayTypeMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 结算方式<BR> 添加AccPayType
	 * @param AccPayType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccPayType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 批量添加AccPayType
	 * @param  AccPayType entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccPayType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 查询AccPayType分页
	 * @param  entityMap RowBounds
	 * @return List<AccPayType>
	 * @throws DataAccessException
	*/
	public List<AccPayType> queryAccPayType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 结算方式<BR> 查询AccPayType所有数据
	 * @param  entityMap
	 * @return List<AccPayType>
	 * @throws DataAccessException
	*/
	public List<AccPayType> queryAccPayType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 查询AccPayTypeByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccPayType queryAccPayTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 删除AccPayType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccPayType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 批量删除AccPayType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccPayType(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 结算方式<BR> 更新AccPayType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccPayType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 批量更新AccPayType
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccPayType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<AccPayType> queryAccPayTypeByExtend(Map<String,Object> entityMap) throws DataAccessException;

	public List<AccPayType> queryAccPayTypeByVouch(
			Map<String, Object> entityMap, RowBounds rowBounds);

	public List<?> queryAccPayTypeByVouch(Map<String, Object> entityMap);

	public int queryAccPayTypeByName(Map<String, Object> entityMap);
}
