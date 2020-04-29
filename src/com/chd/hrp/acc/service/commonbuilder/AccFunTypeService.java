/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.commonbuilder;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccFunType;

/**
* @Title. @Description.
* 支出功能分类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccFunTypeService {

	/**
	 * @Description 
	 * 支出功能分类<BR> 添加AccFunType
	 * @param AccFunType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccFunType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 批量添加AccFunType
	 * @param  AccFunType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccFunType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 查询AccFunType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccFunType(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAccFunTypePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 查询AccFunTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccFunType queryAccFunTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 删除AccFunType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccFunType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 批量删除AccFunType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccFunType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 更新AccFunType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccFunType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 批量更新AccFunType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccFunType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 导入AccFunType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccFunType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 支出功能分类<BR> 继承数据AccFunType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendAccFunType(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 支出功能分类上级编码是否存在
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> qureySurp_code(Map<String,Object> entityMap)throws DataAccessException;
}
