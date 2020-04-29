/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.Cus;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface CusService {

	/**
	 * @Description 添加Cus
	 * @param Cus entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addCus(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Cus
	 * @param  Cus entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchCus(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Cus分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryCus(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询CusByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Cus queryCusByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Cus
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteCus(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Cus
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchCus(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Cus
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateCus(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Cus
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchCus(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Cus
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importCus(Map<String,Object> entityMap)throws DataAccessException;
	
}
