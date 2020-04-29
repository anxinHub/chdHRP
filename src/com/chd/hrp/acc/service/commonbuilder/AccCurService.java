/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.commonbuilder;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccCur;

/**
* @Title. @Description.
* 币种<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccCurService {

	/**
	 * @Description 
	 * 币种<BR> 添加AccCur
	 * @param AccCur entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccCur(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 批量添加AccCur
	 * @param  AccCur entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccCur(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 查询AccCur分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccCur(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 查询AccCurByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccCur queryAccCurByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 删除AccCur
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccCur(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 批量删除AccCur
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccCur(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 更新AccCur
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccCur(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 批量更新AccCur
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccCur(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 导入AccCur
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccCur(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 币种<BR> 继承数据AccCur
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendAccCur(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<AccCur> queryAccCurByList(Map<String,Object> entityMap)throws DataAccessException;
	
}
