/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.Countries;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CountriesService {

	/**
	 * @Description 添加Countries
	 * @param Countries entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCountries(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Countries
	 * @param  Countries entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCountries(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Countries分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCountries(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询CountriesByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Countries queryCountriesByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Countries
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCountries(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Countries
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCountries(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Countries
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCountries(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Countries
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCountries(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Countries
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importCountries(Map<String,Object> entityMap)throws DataAccessException;
	
}
