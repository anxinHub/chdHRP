/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.commonbuilder;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccSubjType;

/**
* @Title. @Description.
* 科目类别<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccSubjTypeService {

	/**
	 * @Description 
	 * 科目类别<BR> 添加AccSubjType
	 * @param AccSubjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 批量添加AccSubjType
	 * @param  AccSubjType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 查询AccSubjType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccSubjType(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 科目类别<BR> 查询AccSubjTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccSubjType queryAccSubjTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 删除AccSubjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 批量删除AccSubjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 更新AccSubjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 批量更新AccSubjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccSubjType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 科目类别<BR> 导入AccSubjType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccSubjType(Map<String,Object> entityMap)throws DataAccessException;
	
}
