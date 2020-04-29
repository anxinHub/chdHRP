/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.commonbuilder;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccEcoType;

/**
* @Title. @Description.
* 支出经济分类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccEcoTypeService {

	/**
	 * @Description 
	 * 支出经济分类<BR> 添加AccEcoType
	 * @param AccEcoType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccEcoType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 批量添加AccEcoType
	 * @param  AccEcoType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccEcoType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 查询AccEcoType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccEcoType(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccEcoTypePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 查询AccEcoTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccEcoType queryAccEcoTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 删除AccEcoType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccEcoType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 批量删除AccEcoType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccEcoType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 更新AccEcoType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccEcoType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 批量更新AccEcoType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccEcoType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR> 导入AccEcoType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccEcoType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类<BR>  继承数据AccEcoType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendAccEcoType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出经济分类上级编码是否存在
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> qureySurp_code(Map<String,Object> entityMap)throws DataAccessException;
}
