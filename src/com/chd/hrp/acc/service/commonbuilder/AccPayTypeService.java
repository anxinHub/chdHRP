/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.commonbuilder;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccPayType;

/**
* @Title. @Description.
* 结算方式<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccPayTypeService {

	/**
	 * @Description 
	 * 结算方式<BR> 添加AccPayType
	 * @param AccPayType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccPayType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 批量添加AccPayType
	 * @param  AccPayType entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccPayType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 查询AccPayType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccPayTypeByVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 查询AccPayType分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccPayType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 查询AccPayTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccPayType queryAccPayTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 删除AccPayType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccPayType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 批量删除AccPayType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccPayType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 更新AccPayType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccPayType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 批量更新AccPayType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccPayType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 导入AccPayType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccPayType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 结算方式<BR> 继承数据AccPayType
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendAccPayType(Map<String,Object> entityMap)throws DataAccessException;
	
}
