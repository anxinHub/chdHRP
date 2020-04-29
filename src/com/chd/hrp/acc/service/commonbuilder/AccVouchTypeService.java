/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.commonbuilder;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccVouchType;

/**
* @Title. @Description.
* 凭证类型<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccVouchTypeService {

	/**
	 * @Description 
	 * 凭证类型<BR> 添加AccVouchType
	 * @param AccVouchType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccVouchType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 批量添加AccVouchType
	 * @param  AccVouchType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccVouchType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 查询AccVouchType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccVouchType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 查询AccVouchTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccVouchType queryAccVouchTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 删除AccVouchType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccVouchType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 批量删除AccVouchType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccVouchType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 更新AccVouchType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccVouchType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 批量更新AccVouchType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccVouchType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 导入AccVouchType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccVouchType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 凭证类型<BR> 继承数据AccVouchType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendAccVouchType(Map<String,Object> entityMap)throws DataAccessException;
	
}
