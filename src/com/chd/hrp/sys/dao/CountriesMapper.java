/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.sys.entity.Countries;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CountriesMapper extends SqlMapper{
	
	/**
	 * @Description 添加Countries
	 * @param Countries entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addCountries(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Countries
	 * @param  Countries entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchCountries(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Countries分页
	 * @param  entityMap RowBounds
	 * @return List<Countries>
	 * @throws DataAccessException
	*/
	public List<Countries> queryCountries(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Countries所有数据
	 * @param  entityMap
	 * @return List<Countries>
	 * @throws DataAccessException
	*/
	public List<Countries> queryCountries(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询CountriesByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Countries queryCountriesByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Countries
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteCountries(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Countries
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchCountries(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Countries
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateCountries(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Countries
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchCountries(List<Map<String, Object>> entityMap)throws DataAccessException;
}
